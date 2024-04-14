package com.aoyou.entertainment.common.constants;

/**
 * ClassName: CacheConstants
 * Package: com.aoyou.entertainment.common.constants
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/3/15 21:11
 * @Version 1.0
 */
public class CacheConstants {
    /**
     * 认证后的用户key前缀
     */
    public static final String LOGIN_USER_KEY = "login:user:";

    /**
     * 缓存的店铺信息key前缀
     */
    public static final String GAME_STORE_KEY = "game:store:";
    /**
     * 双重检测锁的分布式锁key
     */
    public static final String LOCK_GAME_STORE = "lock:game:store:";
    /**
     * 缓存的店铺信息TTL，单位：分钟
     */
    public static final long GAME_STORE_TTL = 10L;
    /**
     * 缓存空值TTL，单位：分钟
     */
    public static final long CACHE_BLANK_TTL = 2L;
}
