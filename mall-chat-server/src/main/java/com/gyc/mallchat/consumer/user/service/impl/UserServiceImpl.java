package com.gyc.mallchat.consumer.user.service.impl;

import com.gyc.mallchat.consumer.user.dao.UserDao;
import com.gyc.mallchat.consumer.user.domain.entity.User;
import com.gyc.mallchat.consumer.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ClassName: UserServiceImpl
 * Package: com.gyc.mallchat.consumer.user.service.impl
 * Description:
 *
 * @Author gyc
 * @Create 2024/4/9 17:06
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserByOpenId(String openId) {
        return userDao.getUserByOpenId(openId);
    }

    @Override
    @Transactional
    public Long register(User insert) {
        userDao.save(insert);
        //todo 用户注册事件，谁关注谁用
        return insert.getId();
    }
}
