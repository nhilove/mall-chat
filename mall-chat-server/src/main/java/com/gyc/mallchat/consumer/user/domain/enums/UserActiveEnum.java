package com.gyc.mallchat.consumer.user.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ClassName: IdmeponentEnum
 * Package: com.gyc.mallchat.consumer.user.domain.enums
 * Description: 发放物品的幂等类型
 *
 * @Author gyc
 * @Create 2024/4/11 16:40
 * @Version 1.0
 */
@AllArgsConstructor
@Getter
public enum UserActiveEnum {

    ONLINE(1, "上线"),
    OFFLINE(2, "离线");

    private final Integer status;
    private final String desc;

}
