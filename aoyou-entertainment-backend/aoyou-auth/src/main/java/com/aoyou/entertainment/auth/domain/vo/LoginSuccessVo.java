package com.aoyou.entertainment.auth.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: LoginSuccessVo
 * Package: com.aoyou.entertainment.auth.domain.vo
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/3/16 1:52
 * @Version 1.0
 */
@Data
public class LoginSuccessVo implements Serializable {
    private String nickname;
    private String token;
}
