package com.aoyou.entertainment.auth.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: LoginDto
 * Package: com.aoyou.entertainment.auth.domain.dto
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/3/15 21:09
 * @Version 1.0
 */
@Data
public class LoginDto implements Serializable {
    private String account;
    private String password;
    private Boolean rememberMe;
}
