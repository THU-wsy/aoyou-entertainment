package com.aoyou.entertainment.auth.mapper;

import com.aoyou.entertainment.common.domain.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClassName: UserMapper
 * Package: com.aoyou.entertainment.auth.mapper
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/3/15 20:29
 * @Version 1.0
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
