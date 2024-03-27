package com.aoyou.entertainment.game.controller;

import com.aoyou.entertainment.common.constants.HttpStatus;
import com.aoyou.entertainment.common.domain.PageResult;
import com.aoyou.entertainment.common.domain.Result;
import com.aoyou.entertainment.common.exception.ServiceException;
import com.aoyou.entertainment.game.domain.dto.QueryGameStoreDto;
import com.aoyou.entertainment.game.domain.entity.GameStore;
import com.aoyou.entertainment.game.domain.vo.GameStoreBaseInfo;
import com.aoyou.entertainment.game.domain.vo.GameStoreSelfBaseInfo;
import com.aoyou.entertainment.game.service.IGameStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * ClassName: GameStoreController
 * Package: com.aoyou.entertainment.game.controller
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/3/16 17:36
 * @Version 1.0
 */
@RestController
@RequestMapping("/game")
public class GameStoreController {
    @Autowired
    private IGameStoreService gameStoreService;

    /**
     * 分页查询所有店铺基本信息
     */
    @PostMapping("/info/list")
    public Result<PageResult<GameStoreBaseInfo>> list(@RequestBody QueryGameStoreDto dto) {
        PageResult<GameStoreBaseInfo> data = gameStoreService.list(dto);
        return Result.ok(data);
    }

    /**
     * 根据店铺id查询某一店铺的所有信息
     */
    @GetMapping("/info/detail/{id}")
    public Result<GameStore> getGameStoreDetail(@PathVariable("id") Long id) {
        GameStore gameStore = gameStoreService.getGameStoreDetail(id);
        if (gameStore == null)
            throw new ServiceException(HttpStatus.NOT_FOUND, "该店不存在");
        return Result.ok(gameStore);
    }

    /**
     * 分页查询该用户的所有店铺
     */
    @PostMapping("/list")
    public Result<PageResult<GameStoreSelfBaseInfo>> listGameStoreSelf(@RequestBody QueryGameStoreDto dto) {
        PageResult<GameStoreSelfBaseInfo> data = gameStoreService.listGameStoreSelf(dto);
        return Result.ok(data);
    }

    /**
     * 新增店铺
     */
    @PostMapping("/detail")
    public Result addGameStore(@Validated @RequestBody GameStore gameStore) {
        gameStoreService.addGameStore(gameStore);
        return Result.ok();
    }

    /**
     * 修改店铺
     */
    @PutMapping("/detail")
    public Result updateGameStore(@Validated @RequestBody GameStore gameStore) {
        gameStoreService.updateGameStore(gameStore);
        return Result.ok();
    }

    @DeleteMapping("/detail")
    public Result removeGameStore(@RequestBody Long[] ids) {
        gameStoreService.removeGameStore(ids);
        return Result.ok();
    }




    /**
     * 临时处理一下文件上传
     */
    @PostMapping("/upload")
    public Result uploadGameStorePicture(@RequestPart("picture") MultipartFile picture) {
        System.out.println(picture);
        return Result.ok("https://himg.bdimg.com/sys/portraitn/item/public.1.fc4cce31.XtVbJS1kKdVpCfCbNXODUw");
    }
}
