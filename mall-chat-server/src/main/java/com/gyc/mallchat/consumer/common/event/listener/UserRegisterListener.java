package com.gyc.mallchat.consumer.common.event.listener;

import com.gyc.mallchat.consumer.common.event.UserRegisterEvent;
import com.gyc.mallchat.consumer.user.domain.entity.User;
import com.gyc.mallchat.consumer.user.domain.enums.IdempotentEnum;
import com.gyc.mallchat.consumer.user.domain.enums.ItemEnum;
import com.gyc.mallchat.consumer.user.service.IUserBackpackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * ClassName: UserRegisterListener
 * Package: com.gyc.mallchat.consumer.common.event.listener
 * Description:
 *
 * @Author gyc
 * @Create 2024/4/11 21:51
 * @Version 1.0
 */
@Slf4j
@Component
public class UserRegisterListener {

    @Autowired
    private IUserBackpackService userBackpackService;

    @Async
    @TransactionalEventListener( value = UserRegisterEvent.class, phase = TransactionPhase.AFTER_COMMIT )
    public void sendCard(UserRegisterEvent userRegisterEvent) {
        User user = userRegisterEvent.getUser();
        //发放改名卡
        userBackpackService.acquireItem(user.getId(), ItemEnum.MODIFY_NAME_CARD.getId(), IdempotentEnum.UID, user.getId().toString());
    }

//    @Async
//    @EventListener(classes = UserRegisterEvent.class)
//    public void sendBadge(UserRegisterEvent event) {
//        User user = event.getUser();
//        int count = userDao.count();// 性能瓶颈，等注册用户多了直接删掉
//        if (count <= 10) {
//            iUserBackpackService.acquireItem(user.getId(), ItemEnum.REG_TOP10_BADGE.getId(), IdempotentEnum.UID, user.getId().toString());
//        } else if (count <= 100) {
//            iUserBackpackService.acquireItem(user.getId(), ItemEnum.REG_TOP100_BADGE.getId(), IdempotentEnum.UID, user.getId().toString());
//        }
//    }

}
