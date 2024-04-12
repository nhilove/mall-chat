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
public enum IdempotentEnum {

    UID(1, "uid"),
    MEG_ID(2, "消息id");

    private final Integer type;
    private final String desc;

}
