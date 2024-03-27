package com.aoyou.entertainment.auth.controller;

import com.aoyou.entertainment.auth.domain.dto.LoginDto;
import com.aoyou.entertainment.auth.service.IUserService;
import com.aoyou.entertainment.common.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: AuthController
 * Package: com.aoyou.entertainment.auth.controller
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/3/15 21:01
 * @Version 1.0
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private IUserService userService;

    /**
     * 处理用户登录请求
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginDto loginDto) {
        return userService.login(loginDto);
    }
}
