package com.aoyou.entertainment.game.service;

import com.aoyou.entertainment.common.domain.PageResult;
import com.aoyou.entertainment.common.domain.Result;
import com.aoyou.entertainment.game.domain.dto.QueryGameStoreDto;
import com.aoyou.entertainment.game.domain.entity.GameStore;
import com.aoyou.entertainment.game.domain.vo.GameStoreBaseInfo;
import com.aoyou.entertainment.game.domain.vo.GameStoreSelfBaseInfo;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * ClassName: IGameStoreService
 * Package: com.aoyou.entertainment.game.service
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/3/16 18:01
 * @Version 1.0
 */
public interface IGameStoreService {
    PageResult<GameStoreBaseInfo> list(QueryGameStoreDto dto);
    GameStore getGameStoreDetail(Long id);
    PageResult<GameStoreSelfBaseInfo> listGameStoreSelf(QueryGameStoreDto dto);
    void addGameStore(GameStore gameStore);
    void updateGameStore(GameStore gameStore);
    void removeGameStore(Long[] ids);
}
