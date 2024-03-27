package com.aoyou.entertainment.auth.service;

import com.aoyou.entertainment.auth.domain.dto.LoginDto;
import com.aoyou.entertainment.auth.domain.vo.LoginSuccessVo;
import com.aoyou.entertainment.common.domain.Result;

/**
 * ClassName: IUserService
 * Package: com.aoyou.entertainment.auth.service
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/3/15 20:30
 * @Version 1.0
 */
public interface IUserService {
    Result<LoginSuccessVo> login(LoginDto loginDto);
}
