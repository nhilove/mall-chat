package com.gyc.mallchat.consumer.user.service.impl;

import com.gyc.mallchat.consumer.user.service.LoginService;
import org.springframework.stereotype.Service;

/**
 * ClassName: LoginServiceImpl
 * Package: com.gyc.mallchat.consumer.user.service.impl
 * Description:
 *
 * @Author gyc
 * @Create 2024/4/9 18:10
 * @Version 1.0
 */
@Service
public class LoginServiceImpl implements LoginService {


    /**
     * 校验token是不是有效
     *
     * @param token
     * @return
     */
    @Override
    public boolean verify(String token) {
        return false;
    }

    /**
     * 刷新token有效期
     *
     * @param token
     */
    @Override
    public void renewalTokenIfNecessary(String token) {

    }

    /**
     * 获取token信息
     *
     * @param id
     * @return
     */
    @Override
    public String login(Long id) {
        return null;
    }

    /**
     * 如果token有效，返回uid
     *
     * @param token
     * @return
     */
    @Override
    public Long getValidUid(String token) {
        return null;
    }
}
