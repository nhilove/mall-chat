package com.gyc.mallchat.consumer.common.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ClassName: CommonErrorEnum
 * Package: com.gyc.mallchat.consumer.common.exception.enums
 * Description:
 *
 * @Author gyc
 * @Create 2024/4/11 10:36
 * @Version 1.0
 */
@AllArgsConstructor
@Getter
public enum CommonErrorEnum implements ErrorEnum {
    BUSINESS_ERROR(0, "{}"),
    PARAMS_INVALID(-2, "参数无效"),
    SYSTEM_ERROR(-1, "系统异常"),
    LOCK_LIMIT(-4, "请求太频繁了，请稍后再试哦~~")
    ;

    private final Integer errorCode;

    private final String des;


    public Integer getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMsg() {
        return des;
    }
}
