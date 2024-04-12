package com.gyc.mallchat.consumer.user.domain.enums;

import io.swagger.models.auth.In;
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
public enum BlackTypeEnum {
    UID(1, "uid"),
    IP(2, "ip"),
    ;

    private final Integer type;
    private final String desc;

    private static Map<Integer, BlackTypeEnum> cache;

    static {
        cache = Arrays.stream(BlackTypeEnum.values()).collect(Collectors.toMap(BlackTypeEnum::getType, Function.identity()));
    }

    public static BlackTypeEnum of(Integer type) {
        return cache.get(type);
    }
}
