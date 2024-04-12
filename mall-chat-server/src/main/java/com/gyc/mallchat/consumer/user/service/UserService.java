package com.gyc.mallchat.consumer.user.service;

import com.gyc.mallchat.consumer.user.domain.vo.res.BadgeResp;
import com.gyc.mallchat.consumer.user.domain.vo.res.UserInfoResp;
import com.gyc.mallchat.consumer.user.domain.entity.User;

import java.util.List;

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

    UserInfoResp getUserInfo(Long uid);

    void modifyName(Long uid, String name);

    List<BadgeResp> getBadges(Long uid);

    void wearBadge(Long uid, Long itemId);

    void black(Long uid, Long uid1);
}
