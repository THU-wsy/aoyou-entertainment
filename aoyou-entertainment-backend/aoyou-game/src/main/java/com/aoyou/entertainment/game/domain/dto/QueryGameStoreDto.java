package com.aoyou.entertainment.game.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: QueryGameStoreDto
 * Package: com.aoyou.entertainment.game.domain.dto
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/3/16 17:42
 * @Version 1.0
 */
@Data
public class QueryGameStoreDto implements Serializable {
    private String name;
    private String city;
    private Integer pageNum;
    private Integer pageSize;
}
