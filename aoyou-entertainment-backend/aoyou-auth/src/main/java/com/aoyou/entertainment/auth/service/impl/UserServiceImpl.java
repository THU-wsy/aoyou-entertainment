package com.aoyou.entertainment.auth.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.aoyou.entertainment.auth.domain.dto.LoginDto;
import com.aoyou.entertainment.auth.domain.vo.LoginSuccessVo;
import com.aoyou.entertainment.auth.mapper.UserMapper;
import com.aoyou.entertainment.auth.service.IUserService;
import com.aoyou.entertainment.common.constants.HttpStatus;
import com.aoyou.entertainment.common.domain.Result;
import com.aoyou.entertainment.common.domain.entity.User;
import com.aoyou.entertainment.common.exception.ServiceException;
import com.aoyou.entertainment.common.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: UserServiceImpl
 * Package: com.aoyou.entertainment.auth.service.impl
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/3/15 20:31
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements IUserService {
    // SpringSecurity中使用AuthenticationManager验证账号密码
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Result<LoginSuccessVo> login(LoginDto loginDto) {
        // 1. 传入要验证的账号和密码
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginDto.getAccount(),
                        loginDto.getPassword()
                );

        // 2. 调用AuthenticationManager的authenticate()方法验证用户名和密码
        // 其底层会调用UserDetailsService的loadUserByUsername()方法获取用户
        User user = null;
        try {
            Authentication authentication =
                    authenticationManager.authenticate(authenticationToken);
            // 3. 获取用户信息
            user = (User) authentication.getPrincipal();
            if (ObjectUtil.isNull(user))
                throw new Exception();
        } catch (Exception e) {
            throw new ServiceException(HttpStatus.ERROR, "用户名或密码错误");
        }

        // 4. 创建jwt
        String token = jwtUtil.createJwt(user);

        // 5. 封装返回结果
        LoginSuccessVo vo = new LoginSuccessVo();
        vo.setNickname(user.getNickname());
        vo.setToken(token);
        return Result.ok(vo);
    }
}
