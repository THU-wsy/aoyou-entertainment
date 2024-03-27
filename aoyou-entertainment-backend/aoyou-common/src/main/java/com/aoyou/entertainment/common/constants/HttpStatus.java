package com.aoyou.entertainment.common.constants;

/**
 * ClassName: HttpStatus
 * Package: com.aoyou.entertainment.common.constants
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/3/15 20:32
 * @Version 1.0
 */
public class HttpStatus {
    /**
     * 操作成功
     */
    public static final int SUCCESS = 200;

    /**
     * 操作已经执行成功，但是没有返回数据
     */
    public static final int NO_CONTENT = 204;

    /**
     * 未认证
     */
    public static final int UNAUTHORIZED = 401;

    /**
     * 权限不足，禁止访问
     */
    public static final int FORBIDDEN = 403;

    /**
     * 资源，服务未找到
     */
    public static final int NOT_FOUND = 404;

    /**
     * 系统内部错误
     */
    public static final int ERROR = 500;
}
