package com.gyc.mallchat.consumer.common.event;

import com.gyc.mallchat.consumer.user.domain.entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * ClassName: UserRegisterEvent
 * Package: com.gyc.mallchat.consumer.common.event
 * Description:
 *
 * @Author gyc
 * @Create 2024/4/11 21:47
 * @Version 1.0
 */
@Getter
public class UserBlackEvent extends ApplicationEvent {

    private User user;

    public UserBlackEvent(Object source, User user) {
        super(source);
        this.user = user;
    }
}
