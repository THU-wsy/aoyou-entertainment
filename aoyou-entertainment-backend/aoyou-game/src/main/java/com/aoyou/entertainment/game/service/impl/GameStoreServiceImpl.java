package com.aoyou.entertainment.game.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import com.aoyou.entertainment.common.constants.HttpStatus;
import com.aoyou.entertainment.common.domain.PageResult;
import com.aoyou.entertainment.common.domain.entity.User;
import com.aoyou.entertainment.common.exception.ServiceException;
import com.aoyou.entertainment.common.utils.SecurityUtil;
import com.aoyou.entertainment.game.domain.dto.QueryGameStoreDto;
import com.aoyou.entertainment.game.domain.entity.GameStore;
import com.aoyou.entertainment.game.domain.vo.GameStoreBaseInfo;
import com.aoyou.entertainment.game.domain.vo.GameStoreSelfBaseInfo;
import com.aoyou.entertainment.game.mapper.GameStoreMapper;
import com.aoyou.entertainment.game.service.IGameStoreService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.aoyou.entertainment.common.constants.CacheConstants.*;

/**
 * ClassName: GameStoreServiceImpl
 * Package: com.aoyou.entertainment.game.service.impl
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/3/16 18:01
 * @Version 1.0
 */
@Service
@Slf4j
public class GameStoreServiceImpl implements IGameStoreService {
    @Autowired
    private GameStoreMapper gameStoreMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private Redisson redisson;

    /**
     * 分页查询店铺的基本信息（id、name、city、star、picture）
     */
    @Override
    public PageResult<GameStoreBaseInfo> list(QueryGameStoreDto dto) {
        // 1. 封装分页参数
        Page<GameStore> page = new Page<>(dto.getPageNum(), dto.getPageSize());

        // 2. 封装查询条件
        LambdaQueryWrapper<GameStore> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(dto.getName()))
            wrapper.like(GameStore::getName, dto.getName());
        if (StrUtil.isNotBlank(dto.getCity()))
            wrapper.eq(GameStore::getCity, dto.getCity());
        // 只查询部分字段
        wrapper.select(GameStore::getId, GameStore::getName,
                GameStore::getCity, GameStore::getStar, GameStore::getPicture);
        // 根据star降序排序，相同则按id升序
        wrapper.orderByDesc(GameStore::getStar).orderByAsc(GameStore::getId);

        // 3. 进行分页查询
        gameStoreMapper.selectPage(page, wrapper);

        // 4. 封装查询结果
        PageResult<GameStoreBaseInfo> pageResult = new PageResult<>();

        List<GameStoreBaseInfo> list = page.getRecords().stream().map(gameStore -> {
            GameStoreBaseInfo gameStoreBaseInfo = new GameStoreBaseInfo();
            BeanUtil.copyProperties(gameStore, gameStoreBaseInfo);
            return gameStoreBaseInfo;
        }).toList();
        pageResult.setList(list);
        pageResult.setTotal(page.getTotal());

        return pageResult;
    }

    /**
     * 根据id查询某一店铺的具体信息
     * 核心：
     * - 缓存读写策略：旁路缓存模式
     * - 缓存穿透问题：采用缓存无效key的方式解决
     * - 缓存击穿问题：采用双重检测锁解决
     */
    @Override
    public GameStore getGameStoreDetail(Long id) {
        String key = GAME_STORE_KEY + id;
        // 1. 从Redis查询店铺信息
        String gameStoreJson = redisTemplate.opsForValue().get(key);

        // 1.1 如果缓存存在，且不为空值，则直接返回
        if (StrUtil.isNotBlank(gameStoreJson)) {
            return JSONObject.parseObject(gameStoreJson, GameStore.class);
        }
        // 1.2 如果缓存存在，但为空值，也就是缓存的无效key，则抛出异常
        if (gameStoreJson != null)
            throw new ServiceException(HttpStatus.NOT_FOUND, "该店不存在");
        // 1.3 如果缓存不存在，则接下来准备查询数据库

        // 2. 使用双重检测锁来解决缓存击穿问题，首先获取分布式锁
        RLock rLock = redisson.getLock(LOCK_GAME_STORE + id);
        rLock.lock();
        try {
            // 3. 二次查询Redis
            gameStoreJson = redisTemplate.opsForValue().get(key);
            // 3.1 如果缓存存在，且不为空值，则直接返回
            if (StrUtil.isNotBlank(gameStoreJson)) {
                return JSONObject.parseObject(gameStoreJson, GameStore.class);
            }
            // 3.2 如果缓存存在，但为空值，也就是缓存的无效key，则抛出异常
            if (gameStoreJson != null)
                throw new ServiceException(HttpStatus.NOT_FOUND, "该店不存在");

            // 4. 如果缓存不存在，则真正查询数据库
            GameStore gameStore = gameStoreMapper.selectById(id);
            log.info("根据id查询店铺的具体信息====>查询数据库，id：{}", id);

            // 4.1 如果数据库中不存在，则缓存无效key，并抛出异常
            if (gameStore == null) {
                redisTemplate.opsForValue().set(key, "",
                        CACHE_BLANK_TTL, TimeUnit.MINUTES
                );
                throw new ServiceException(HttpStatus.NOT_FOUND, "该店不存在");
            }
            // 4.2 如果数据库中存在，则缓存到Redis
            redisTemplate.opsForValue().set(
                    key, JSONObject.toJSONString(gameStore),
                    GAME_STORE_TTL, TimeUnit.MINUTES
            );
            return gameStore;
        } finally {
            rLock.unlock();
        }
    }

    /**
     * 分页查询用户的店铺的基本信息（id、name、city、star、address）
     */
    @Override
    public PageResult<GameStoreSelfBaseInfo> listGameStoreSelf(QueryGameStoreDto dto) {
        // 1. 封装分页参数
        Page<GameStore> page = new Page<>(dto.getPageNum(), dto.getPageSize());

        // 2. 获取用户信息
        String nickname = SecurityUtil.getNickname();
        // log.info("用户昵称===>{}", nickname);

        // 3. 封装查询条件
        LambdaQueryWrapper<GameStore> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GameStore::getOwner, nickname);
        if (StrUtil.isNotBlank(dto.getName()))
            wrapper.like(GameStore::getName, dto.getName());

        // 只查询部分字段
        wrapper.select(GameStore::getId, GameStore::getName,
                GameStore::getCity, GameStore::getStar, GameStore::getAddress);

        // 4. 进行分页查询
        gameStoreMapper.selectPage(page, wrapper);

        // 5. 封装查询结果
        PageResult<GameStoreSelfBaseInfo> pageResult = new PageResult<>();

        List<GameStoreSelfBaseInfo> list = page.getRecords().stream().map(gameStore -> {
            GameStoreSelfBaseInfo gameStoreSelf = new GameStoreSelfBaseInfo();
            BeanUtil.copyProperties(gameStore, gameStoreSelf);
            return gameStoreSelf;
        }).toList();
        pageResult.setList(list);
        pageResult.setTotal(page.getTotal());

        return pageResult;
    }

    /**
     * 新增店铺
     */
    @Transactional
    @Override
    public void addGameStore(GameStore gameStore) {
        // 1. 获取用户信息
        User user = SecurityUtil.getLoginUser();
        if (ObjectUtil.isNull(user))
            throw new ServiceException(HttpStatus.UNAUTHORIZED, "用户未登录");
        gameStore.setOwner(user.getNickname());
        gameStore.setMobile(user.getMobile());

        // 2. 新增店铺
        int row = gameStoreMapper.insert(gameStore);
        if (row == 0)
            throw new ServiceException(HttpStatus.ERROR, "新增店铺失败");
    }

    /**
     * 修改店铺（先更新数据库，再删除缓存）
     */
    @Transactional
    @Override
    public void updateGameStore(GameStore gameStore) {
        Long id = gameStore.getId();
        if (id == null)
            throw new ServiceException(HttpStatus.ERROR, "店铺不存在");

        // 1. 更新数据库
        int row = gameStoreMapper.updateById(gameStore);
        if (row == 0)
            throw new ServiceException(HttpStatus.ERROR, "修改店铺失败");

        // 2. 删除缓存
        redisTemplate.delete(GAME_STORE_KEY + id);
    }

    /**
     * 删除店铺（先更新数据库，再删除缓存）
     */
    @Transactional
    @Override
    public void removeGameStore(Long[] ids) {
        if (ids == null || ids.length == 0)
            return;

        // 1. 更新数据库
        int row = gameStoreMapper.deleteBatchIds(Arrays.asList(ids));
        if (row == 0)
            throw new ServiceException(HttpStatus.ERROR, "删除店铺失败");

        // 2. 删除缓存
        for (Long id : ids) {
            redisTemplate.delete(GAME_STORE_KEY + id);
        }
    }
}
