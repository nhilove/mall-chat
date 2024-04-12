package com.gyc.mallchat.consumer.user.service.impl;

import com.gyc.mallchat.consumer.common.annotation.RedissonLock;
import com.gyc.mallchat.consumer.common.service.LockService;
import com.gyc.mallchat.consumer.user.dao.UserBackpackDao;
import com.gyc.mallchat.consumer.user.domain.entity.UserBackpack;
import com.gyc.mallchat.consumer.user.domain.enums.IdempotentEnum;
import com.gyc.mallchat.consumer.user.service.IUserBackpackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * ClassName: UserBackpackServiceImpl
 * Package: com.gyc.mallchat.consumer.user.service.impl
 * Description:
 *
 * @Author gyc
 * @Create 2024/4/11 16:47
 * @Version 1.0
 */
@Service
@Slf4j
public class UserBackpackServiceImpl implements IUserBackpackService {

    @Autowired
    private LockService lockService;


    @Autowired
    private UserBackpackDao userBackpackDao;

    @Autowired
    @Lazy
    private UserBackpackServiceImpl userBackpackService;

    /**
     * 发放物品（幂等）
     *
     * @param uid            用户id
     * @param itemId         物品id
     * @param idempotentEnum 幂等类型
     * @param businessId     幂等唯一标识
     */

    @Override
    public void acquireItem(Long uid, Long itemId, IdempotentEnum idempotentEnum, String businessId) {
        String idempotent = getIdempotent(itemId, idempotentEnum, businessId);
        //方法自调用上面有切面注解、事务注解等时要把当前类注入进来，在调用，注入时要懒加载防止循环依赖，否则上面的注解不生效
        userBackpackService.doAcquireItem(uid, itemId, idempotent);
    }

    @RedissonLock( key = "#idempotent", waitTime = 5000 )
    public void doAcquireItem(Long uid, Long itemId, String idempotent) {
        UserBackpack userBackpack = userBackpackDao.getByIdempotent(idempotent);
        if (Objects.nonNull(userBackpack)) {//说明做过了，直接返回
            return;
        }
        //todo 业务检查

        //发放物品
        UserBackpack insert = UserBackpack.builder()
                .uid(uid)
                .itemId(itemId)
                .idempotent(idempotent)
                .build();
        userBackpackDao.save(insert);
    }


    /**
     * 获取幂等号
     *
     * @param itemId
     * @param idempotentEnum
     * @param businessId
     * @return
     */
    private String getIdempotent(Long itemId, IdempotentEnum idempotentEnum, String businessId) {
        return String.format("%d_%d_%s", itemId, idempotentEnum.getType(), businessId);
    }
}
