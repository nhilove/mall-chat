package com.gyc.mallchat.consumer.user.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Description: 权限
 * Date: 2023-04-12
 */
@AllArgsConstructor
@Getter
public enum RoleEnum {
    ADMIN(1l, "超级管理员"),
    MALL_CHAT_MANAGE(2l, "抹茶聊天管理者"),
    ;

    private final Long id;
    private final String desc;

    private static Map<Long, RoleEnum> cache;

    static {
        cache = Arrays.stream(RoleEnum.values()).collect(Collectors.toMap(RoleEnum::getId, Function.identity()));
    }

    public static RoleEnum of(Integer type) {
        return cache.get(type);
    }
}
