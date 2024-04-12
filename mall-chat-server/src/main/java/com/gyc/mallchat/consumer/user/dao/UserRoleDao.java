package com.gyc.mallchat.consumer.user.dao;

import com.gyc.mallchat.consumer.user.domain.entity.UserRole;
import com.gyc.mallchat.consumer.user.mapper.UserRoleMapper;
import com.gyc.mallchat.consumer.user.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户角色关系表 服务实现类
 * </p>
 *
 * @author gyc
 * @since 2024-04-12
 */
@Service
public class UserRoleDao extends ServiceImpl<UserRoleMapper, UserRole> {

    public List<UserRole> listById(Long uid) {
        return lambdaQuery().eq(UserRole::getUid, uid)
                .list();

    }
}
