package com.gyc.mallchat.consumer.common.event.listener;

import com.gyc.mallchat.consumer.common.event.UserOnlineEvent;
import com.gyc.mallchat.consumer.user.dao.UserDao;
import com.gyc.mallchat.consumer.user.domain.entity.User;
import com.gyc.mallchat.consumer.user.domain.enums.UserActiveEnum;
import com.gyc.mallchat.consumer.user.service.IUserBackpackService;
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
public class UserOnlineListener {

    @Autowired
    private IpService ipService;

    @Autowired
    private UserDao userDao;

    @Async
    @TransactionalEventListener( value = UserOnlineEvent.class, phase = TransactionPhase.AFTER_COMMIT,fallbackExecution = true )
    public void saveDB(UserOnlineEvent event) {
        //fallbackExecution = true如果里面调用没有开启事务，就会导致外面的事务失效，配置为true就可以避免
        //用户上线更新用户的信息
        User user = event.getUser();
        User update = new User();
        update.setLastOptTime(user.getLastOptTime());
        update.setId(user.getId());
        update.setIpInfo(user.getIpInfo());
        update.setActiveStatus(UserActiveEnum.ONLINE.getStatus());
        userDao.updateById(update);
        //用户ip解析
        ipService.refreshIpDetailAsync(user.getId());
    }

}
