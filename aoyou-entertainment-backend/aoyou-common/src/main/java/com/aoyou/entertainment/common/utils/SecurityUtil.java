package com.aoyou.entertainment.common.utils;

import cn.hutool.core.util.ObjectUtil;
import com.aoyou.entertainment.common.constants.HttpStatus;
import com.aoyou.entertainment.common.domain.entity.User;
import com.aoyou.entertainment.common.exception.ServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * ClassName: SecurityUtil
 * Package: com.aoyou.entertainment.common.utils
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/3/16 0:24
 * @Version 1.0
 */
public class SecurityUtil {
    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取用户
     */
    public static User getLoginUser() {
        return (User) getAuthentication().getPrincipal();
    }

    /**
     * 获取用户id
     */
    public static Long getUserId() {
        User user = getLoginUser();
        if (ObjectUtil.isNull(user))
            throw new ServiceException(HttpStatus.UNAUTHORIZED, "用户未登录");
        return user.getId();
    }

    /**
     * 获取用户名
     */
    public static String getUsername() {
        User user = getLoginUser();
        if (ObjectUtil.isNull(user))
            throw new ServiceException(HttpStatus.UNAUTHORIZED, "用户未登录");
        return user.getUsername();
    }

    /**
     * 获取用户昵称
     */
    public static String getNickname() {
        User user = getLoginUser();
        if (ObjectUtil.isNull(user))
            throw new ServiceException(HttpStatus.UNAUTHORIZED, "用户未登录");
        return user.getNickname();
    }
}
