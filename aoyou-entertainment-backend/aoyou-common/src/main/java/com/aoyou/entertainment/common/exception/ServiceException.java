package com.aoyou.entertainment.common.exception;

import lombok.Data;

/**
 * ClassName: ServiceException
 * Package: com.aoyou.entertainment.common.exception
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/3/15 20:48
 * @Version 1.0
 */
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private int code;
    private String message;

    public ServiceException() {
    }

    public ServiceException(String message) {
        this.message = message;
    }

    public ServiceException(int code, String message) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
