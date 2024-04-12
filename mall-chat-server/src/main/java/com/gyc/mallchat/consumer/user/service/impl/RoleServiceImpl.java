package com.gyc.mallchat.consumer.user.service.impl;

import com.gyc.mallchat.consumer.user.domain.enums.RoleEnum;
import com.gyc.mallchat.consumer.user.service.IRoleService;
import com.gyc.mallchat.consumer.user.service.cache.UserCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * ClassName: RoleServiceImpl
 * Package: com.gyc.mallchat.consumer.user.service.impl
 * Description:
 *
 * @Author gyc
 * @Create 2024/4/12 16:06
 * @Version 1.0
 */
@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private UserCache userCache;

    /**
     * 是否有权限
     *
     * @param uid
     * @param roleEnum
     * @return
     */
    @Override
    public boolean hasPower(Long uid, RoleEnum roleEnum) {
        //每个用户的权限经常用到做个缓存存起来
        Set<Long> roleSet = userCache.getRoleSet(uid);
        return isAdmin(roleSet) || roleSet.contains(roleEnum.getId());
    }

    private boolean isAdmin(Set<Long> roleSet) {
        return roleSet.contains(RoleEnum.ADMIN.getId());
    }
}
