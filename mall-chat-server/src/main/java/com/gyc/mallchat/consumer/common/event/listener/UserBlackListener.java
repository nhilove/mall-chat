package com.gyc.mallchat.consumer.common.event.listener;

import com.gyc.mallchat.consumer.common.event.UserBlackEvent;
import com.gyc.mallchat.consumer.user.dao.UserDao;
import com.gyc.mallchat.consumer.user.domain.entity.User;
import com.gyc.mallchat.consumer.user.service.IpService;
import com.gyc.mallchat.consumer.user.service.cache.UserCache;
import com.gyc.mallchat.consumer.websocket.service.WebSocketService;
import com.gyc.mallchat.consumer.websocket.service.adapter.WebSocketAdapter;
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
public class UserBlackListener {

    @Autowired
    private IpService ipService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserCache userCache;

    @Autowired
    private WebSocketService webSocketService;

    @Async
    @TransactionalEventListener( value = UserBlackEvent.class, phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true )
    public void sendMsg(UserBlackEvent event) {
        User user = event.getUser();
        webSocketService.senMsgToAll(WebSocketAdapter.buildBlackResp(user));
    }

    @Async
    @TransactionalEventListener( value = UserBlackEvent.class, phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true )
    public void changUserStatus(UserBlackEvent event) {
        userDao.invalidUid(event.getUser().getId());
    }

    @Async
    @TransactionalEventListener( value = UserBlackEvent.class, phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true )
    public void evictCache(UserBlackEvent event) {
        userCache.evictCache();
    }

}
