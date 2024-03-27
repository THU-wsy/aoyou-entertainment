package com.aoyou.entertainment.game.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
     */
    @Override
    public GameStore getGameStoreDetail(Long id) {
        return gameStoreMapper.selectById(id);
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
     * 修改店铺
     */
    @Transactional
    @Override
    public void updateGameStore(GameStore gameStore) {
        int row = gameStoreMapper.updateById(gameStore);
        if (row == 0)
            throw new ServiceException(HttpStatus.ERROR, "修改店铺失败");
    }

    /**
     * 删除店铺
     */
    @Transactional
    @Override
    public void removeGameStore(Long[] ids) {
        int row = gameStoreMapper.deleteBatchIds(Arrays.asList(ids));
        if (row == 0)
            throw new ServiceException(HttpStatus.ERROR, "删除店铺失败");
    }
}
