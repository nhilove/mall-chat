package com.gyc.mallchat.consumer.user.service;

import com.gyc.mallchat.consumer.user.domain.enums.RoleEnum;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author gyc
 * @since 2024-04-12
 */
public interface IRoleService  {

    /**
     * 是否有权限
     * @param uid
     * @param roleEnum
     * @return
     */
    boolean hasPower(Long uid, RoleEnum roleEnum);
}
