package com.gyc.mallchat.consumer.common.event.listener;

import com.gyc.mallchat.consumer.common.event.UserApplyEvent;
import com.gyc.mallchat.consumer.common.event.UserOnlineEvent;
import com.gyc.mallchat.consumer.user.dao.UserDao;
import com.gyc.mallchat.consumer.user.domain.entity.User;
import com.gyc.mallchat.consumer.user.domain.enums.UserActiveEnum;
import com.gyc.mallchat.consumer.user.service.IpService;
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
public class UserApplyListener {



    @Async
    @TransactionalEventListener( value = UserApplyEvent.class, phase = TransactionPhase.AFTER_COMMIT,fallbackExecution = true )
    public void notifyFriend(UserApplyEvent event) {

    }
}
