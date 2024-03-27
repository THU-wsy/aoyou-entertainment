package com.aoyou.entertainment.auth.service.impl;

import com.aoyou.entertainment.auth.mapper.UserMapper;
import com.aoyou.entertainment.common.domain.entity.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: AoyouUserDetailsService
 * Package: com.aoyou.entertainment.auth.service.impl
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/3/15 21:35
 * @Version 1.0
 */
@Service
public class AoyouUserDetailsService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 根据account查询用户
     * @param account 用户名/手机号/邮箱
     */
    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        // TODO 验证账号类型，这里为了我们简便，直接使用用户名

        // 1. 根据账号查询用户（同时查询出角色信息）
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>().eq(User::getUsername, account);
        User user = userMapper.selectOne(wrapper);
        if (user == null)
            throw new UsernameNotFoundException("用户名或密码错误");

        // 2. 根据RBAC权限模型，多表查询用户权限，这里为了简便我们直接省略
        List<String> perms = new ArrayList<>();
        perms.add("all:perm");
        user.setPerms(perms);

        return user;
    }
}
