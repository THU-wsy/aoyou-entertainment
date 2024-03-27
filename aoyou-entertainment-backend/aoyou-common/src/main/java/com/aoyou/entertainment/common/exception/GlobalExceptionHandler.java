package com.aoyou.entertainment.common.exception;

import com.aoyou.entertainment.common.constants.HttpStatus;
import com.aoyou.entertainment.common.domain.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * ClassName: GlobalExceptionHandler
 * Package: com.aoyou.entertainment.common.exception
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/3/15 20:52
 * @Version 1.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理项目的自定义异常
     */
    @ExceptionHandler(ServiceException.class)
    public Result serviceExceptionHandler(ServiceException e) {
        log.error("【service异常】{}", e.getMessage());
        return Result.fail(e.getCode(), e.getMessage());
    }

    /**
     * 处理其他异常
     */
    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception e) {
        log.error("【服务器异常】{}", e.getMessage());
        return Result.fail(HttpStatus.ERROR, "操作异常");
    }
}
