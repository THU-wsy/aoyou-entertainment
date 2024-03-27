package com.aoyou.entertainment.game.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: GameStoreSelfBaseInfo
 * Package: com.aoyou.entertainment.game.domain.vo
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/3/16 17:50
 * @Version 1.0
 */
@Data
public class GameStoreSelfBaseInfo implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String name;
    private String city;
    private Integer star;
    private String address;
}
