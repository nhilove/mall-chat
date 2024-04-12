package com.gyc.mallchat.consumer.user.dao;

import com.gyc.mallchat.consumer.user.domain.entity.Role;
import com.gyc.mallchat.consumer.user.mapper.RoleMapper;
import com.gyc.mallchat.consumer.user.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author gyc
 * @since 2024-04-12
 */
@Service
public class RoleDao extends ServiceImpl<RoleMapper, Role>  {

}
