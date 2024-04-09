package com.gyc.mallchat.consumer.websocket.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * ClassName: WSReqTypeEnum
 * Package: com.gyc.mallchat.consumer.websocket.domain.enums
 * Description: 请求状态枚举类
 *
 * @Author gyc
 * @Create 2024/4/3 11:08
 * @Version 1.0
 */
@Getter
@AllArgsConstructor
public enum WSReqTypeEnum {

    LOGIN(1, "请求登录二维码"),
    HEARTBEAT(2, "心跳包"),
    AUTHORIZE(3, "登录认证"),
    ;

    private final Integer type;
    private final String desc;

    private static Map<Integer, WSReqTypeEnum> cache;

    static {
        cache = Arrays.stream(WSReqTypeEnum.values()).collect(Collectors.toMap(WSReqTypeEnum::getType, Function.identity()));
    }

    public static WSReqTypeEnum of(Integer type) {
        return cache.get(type);
    }
}
