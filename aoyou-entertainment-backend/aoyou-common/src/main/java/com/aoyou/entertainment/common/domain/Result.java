package com.aoyou.entertainment.common.domain;

import com.aoyou.entertainment.common.constants.HttpStatus;
import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: Result
 * Package: com.aoyou.entertainment.common.domain
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/3/15 20:37
 * @Version 1.0
 */
@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 成功 */
    public static final int SUCCESS = HttpStatus.SUCCESS;

    /** 失败 */
    public static final int FAIL = HttpStatus.ERROR;

    private int code;
    private String msg;
    private T data;

    private static <T> Result<T> restResult(int code, String msg, T data) {
        Result<T> apiResult = new Result<>();
        apiResult.setCode(code);
        apiResult.setMsg(msg);
        apiResult.setData(data);
        return apiResult;
    }

    public static <T> Result<T> ok() {
        return restResult(SUCCESS, "操作成功", null);
    }

    public static <T> Result<T> ok(T data) {
        return restResult(SUCCESS, "操作成功", data);
    }

    public static <T> Result<T> ok(T data, String msg) {
        return restResult(SUCCESS, msg, data);
    }

    public static <T> Result<T> fail() {
        return restResult(FAIL, "操作失败", null);
    }

    public static <T> Result<T> fail(String msg) {
        return restResult(FAIL, msg, null);
    }

    public static <T> Result<T> fail(T data) {
        return restResult(FAIL, "操作失败", data);
    }

    public static <T> Result<T> fail(T data, String msg) {
        return restResult(FAIL, msg, data);
    }

    public static <T> Result<T> fail(int code, String msg) {
        return restResult(code, msg, null);
    }
}
