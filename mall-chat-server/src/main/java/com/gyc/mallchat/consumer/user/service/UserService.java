package com.gyc.mallchat.consumer.user.service;

import com.gyc.mallchat.consumer.user.domain.entity.User;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author gyc
 * @since 2024-04-08
 */
public interface UserService {

    User getUserByOpenId(String openId);

    Long register(User insert);
}
