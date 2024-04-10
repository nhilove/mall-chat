package com.gyc.mallchat.consumer.user.service.impl;

import com.gyc.mallchat.consumer.common.constant.RedisKey;
import com.gyc.mallchat.consumer.common.utils.JwtUtils;
import com.gyc.mallchat.consumer.common.utils.RedisUtils;
import com.gyc.mallchat.consumer.user.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

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

    public static final int USER_TOKEN_EXPIRE = 30;
    @Autowired
    private JwtUtils jwtUtils;


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
     * @param uid
     * @return
     */
    @Override
    public String login(Long uid) {
        String token = jwtUtils.createToken(uid);
        //存到redis中
        RedisUtils.set(getUserTokenKey(uid), token, USER_TOKEN_EXPIRE, TimeUnit.DAYS);
        return token;
    }


    private String getUserTokenKey(Long uid) {
        return RedisKey.getUserTokenKey(RedisKey.USER_TOKEN_KEY, uid);
    }


    /**
     * 如果token有效，返回uid
     *
     * @param token
     * @return
     */
    @Override
    public Long getValidUid(String token) {
        Long uid = jwtUtils.getUidOrNull(token);
        if (Objects.isNull(uid)) {
            return null;
        }
        String oldToken = RedisUtils.getStr(getUserTokenKey(uid));
        //新的token和老token一致，才能返回uid，不一致说明更新了token
        return Objects.equals(oldToken, token) ? uid : null;
    }
}
