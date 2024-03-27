package com.aoyou.entertainment.common.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: AoyouUser
 * Package: com.aoyou.entertainment.common.domain.entity
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/3/15 20:23
 * @Version 1.0
 */
@Data
@TableName("tb_user")
public class User implements UserDetails {
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String username;
    private String nickname;
    private String email;
    private String mobile;
    private Integer sex;
    private String password;
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    private String remark;
    @TableLogic
    private Integer deleted;
    // 用户的权限信息，根据RBAC模型，需要通过数据库多表联查得到
    @TableField(exist = false)
    private List<String> perms = new ArrayList<>();
    @TableField(exist = false)
    private String uuid;

    /**
     * 重写UserDetails的核心方法主要有：获取用户名、获取密码、获取权限
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 通过stream将List<String>转为Collection<GrantedAuthority>
        return perms.stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return deleted.equals(0);
    }

    @Override
    public boolean isAccountNonLocked() {
        return deleted.equals(0);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return deleted.equals(0);
    }

    @Override
    public boolean isEnabled() {
        return deleted.equals(0);
    }
}
