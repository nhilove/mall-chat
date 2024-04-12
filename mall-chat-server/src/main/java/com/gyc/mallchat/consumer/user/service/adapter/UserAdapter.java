package com.gyc.mallchat.consumer.user.service.adapter;

import cn.hutool.core.bean.BeanUtil;
import com.gyc.mallchat.consumer.common.domain.enums.YesOrNoEnum;
import com.gyc.mallchat.consumer.user.domain.entity.User;
import com.gyc.mallchat.consumer.user.domain.entity.UserBackpack;
import com.gyc.mallchat.consumer.user.domain.vo.res.BadgeResp;
import com.gyc.mallchat.consumer.user.domain.vo.res.UserInfoResp;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;

import java.util.ArrayList;
import java.util.List;

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

    public static UserInfoResp buildUserInfoResp(User user, Integer count) {
        UserInfoResp userInfoResp = new UserInfoResp();
        BeanUtil.copyProperties(user, userInfoResp);
        userInfoResp.setModifyChangeName(count);
        return userInfoResp;
    }

    public static List<BadgeResp> buildUserBackpack(List<UserBackpack> badgesByUserId) {
        List<BadgeResp> res = new ArrayList<>();
        for (UserBackpack userBackpack : badgesByUserId) {
            BadgeResp badgeResp = new BadgeResp();
            BeanUtil.copyProperties(userBackpack, badgeResp);
            badgeResp.setWearing(userBackpack.getStatus());
            badgeResp.setObtain(YesOrNoEnum.YES.getStatus());
            res.add(badgeResp);
        }
        return res;
    }
}
