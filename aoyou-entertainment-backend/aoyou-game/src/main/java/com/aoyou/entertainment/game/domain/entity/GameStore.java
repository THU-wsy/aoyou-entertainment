package com.aoyou.entertainment.game.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * ClassName: GameStore
 * Package: com.aoyou.entertainment.game.domain.entity
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/3/16 17:37
 * @Version 1.0
 */
@Data
@TableName("tb_game_store")
public class GameStore implements Serializable {
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @NotBlank(message = "店铺名称不能为空")
    private String name;
    @NotBlank(message = "所在城市不能为空")
    private String city;
    private Integer star;
    @NotBlank(message = "店铺图片不能为空")
    private String picture;
    @NotBlank(message = "详细地址不能为空")
    private String address;
    private Integer price;
    private String detail;
    private String owner;
    private String mobile;
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    @TableLogic
    private Integer deleted;
}
