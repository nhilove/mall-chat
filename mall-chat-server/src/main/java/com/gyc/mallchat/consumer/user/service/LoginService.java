package com.gyc.mallchat.consumer.user.service;

/**
 * ClassName: LoginService
 * Package: com.gyc.mallchat.consumer.user.service
 * Description:
 *
 * @Author gyc
 * @Create 2024/4/9 18:10
 * @Version 1.0
 */
public interface LoginService {


    /**
     * 刷新token有效期
     *
     * @param token
     */
    void renewalTokenIfNecessary(String token);

    /**
     * 登录成功，获取token
     *
     * @param uid
     * @return 返回token
     */
    String login(Long uid);

    /**
     * 如果token有效，返回uid
     *
     * @param token
     * @return
     */
    Long getValidUid(String token);
}
