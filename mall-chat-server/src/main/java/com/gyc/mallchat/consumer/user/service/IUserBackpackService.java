package com.gyc.mallchat.consumer.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gyc.mallchat.consumer.user.domain.entity.UserBackpack;
import com.gyc.mallchat.consumer.user.domain.enums.IdempotentEnum;

/**
 * <p>
 * 用户背包表 服务类
 * </p>
 *
 * @author gyc
 * @since 2024-04-10
 */
public interface IUserBackpackService {

    /**
     * 发放物品（幂等）
     *
     * @param uid            用户id
     * @param itemId         物品id
     * @param idempotentEnum 幂等类型
     * @param businessId     幂等唯一标识
     */
    void acquireItem(Long uid, Long itemId, IdempotentEnum idempotentEnum, String businessId);

}
