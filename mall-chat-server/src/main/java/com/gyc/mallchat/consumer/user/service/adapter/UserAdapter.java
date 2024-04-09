package com.gyc.mallchat.consumer.user.service.adapter;

import com.gyc.mallchat.consumer.user.domain.entity.User;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;

/**
 * ClassName: UserAdapter
 * Package: com.gyc.mallchat.consumer.user.service.adapter
 * Description:
 *
 * @Author gyc
 * @Create 2024/4/9 17:26
 * @Version 1.0
 */
public class UserAdapter {

    public static User buildUserSava(String openId) {
        return User.builder().openId(openId).build();
    }

    public static User buildUserAuthorized(Long uid, WxOAuth2UserInfo userInfo) {
        String avatar = userInfo.getHeadImgUrl();
        String name = userInfo.getNickname();
        User user = new User();
        user.setName(name);
        user.setAvatar(avatar);
        user.setId(uid);
        return user;
    }
}
