package com.gyc.mallchat.consumer.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gyc.mallchat.consumer.common.domain.enums.YesOrNoEnum;
import com.gyc.mallchat.consumer.user.domain.entity.User;
import com.gyc.mallchat.consumer.user.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author gyc
 * @since 2024-04-08
 */
@Service
public class UserDao extends ServiceImpl<UserMapper, User> {

    public User getUserByOpenId(String openId) {
        return lambdaQuery().eq(User::getOpenId, openId).one();
    }

    public User getUserByName(String name) {
        return lambdaQuery().eq(User::getName, name).one();
    }

    public void invalidUid(Long id) {
        lambdaUpdate().eq(User::getId, id)
                .set(User::getStatus, YesOrNoEnum.YES.getStatus())
                .update();
    }

    /**
     * 批量查询好友信息
     *
     * @param uids
     * @return
     */
    public List<User> getUserFriendList(List<Long> uids) {
        return lambdaQuery()
                .in(User::getId, uids)
                .select(User::getId, User::getActiveStatus, User::getAvatar, User::getName)
                .list();
    }
}
