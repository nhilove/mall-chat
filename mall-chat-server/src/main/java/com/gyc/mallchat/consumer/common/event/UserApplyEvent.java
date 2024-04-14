package com.gyc.mallchat.consumer.common.event;

import com.gyc.mallchat.consumer.user.domain.entity.User;
import com.gyc.mallchat.consumer.user.domain.entity.UserApply;
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
public class UserApplyEvent extends ApplicationEvent {

    private UserApply userApply;

    public UserApplyEvent(Object source, UserApply userApply) {
        super(source);
        this.userApply = userApply;
    }
}
