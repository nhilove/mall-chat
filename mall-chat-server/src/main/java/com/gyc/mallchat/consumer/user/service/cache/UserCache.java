package com.gyc.mallchat.consumer.user.service.cache;


import com.gyc.mallchat.consumer.user.dao.BlackDao;
import com.gyc.mallchat.consumer.user.dao.UserRoleDao;
import com.gyc.mallchat.consumer.user.domain.entity.Black;
import com.gyc.mallchat.consumer.user.domain.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Description: 用户相关缓存
 * <p>
 * Date: 2024-4-12
 */
@Component
public class UserCache {//todo 多级缓存

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private BlackDao blackDao;

    @Cacheable( cacheNames = "role", key = "'roles:'+#uid" )
    public Set<Long> getRoleSet(Long uid) {
        List<UserRole> userRoles = userRoleDao.listById(uid);
        return userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toSet());
    }

    @Cacheable( cacheNames = "role", key = "'blackList:'" )
    public Map<Integer, Set<String>> getBlackUserMap() {
        Map<Integer, List<Black>> collect =
                blackDao.list().stream().collect(Collectors.groupingBy(Black::getType));
        Map<Integer, Set<String>> result = new HashMap<>();
        collect.forEach((type, list) -> {
            result.put(type, list.stream().map(Black::getTarget).collect(Collectors.toSet()));
        });
        return result;
    }

    @Cacheable( cacheNames = "role", key = "'blackList:'" )
    public Map<Integer, Set<String>> evictCache() {
        return null;
    }
}
