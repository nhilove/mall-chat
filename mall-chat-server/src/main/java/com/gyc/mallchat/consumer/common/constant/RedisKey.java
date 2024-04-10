package com.gyc.mallchat.consumer.common.constant;

/**
 * ClassName: RedisKey
 * Package: com.gyc.mallchat.consumer.common.constant
 * Description:
 *
 * @Author gyc
 * @Create 2024/4/10 10:32
 * @Version 1.0
 */
public class RedisKey {

    public static final String BASE_KEY = "mallchat:chat";

    public static final String USER_TOKEN_KEY = "userToken:uid_%d";


    public static String getUserTokenKey(String key, Object... o) {
        return BASE_KEY + String.format(key, o);
    }
}
