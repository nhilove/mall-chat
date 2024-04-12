package com.gyc.mallchat.consumer.common.utils;

import com.gyc.mallchat.consumer.common.domain.dto.RequestInfo;

/**
 * ClassName: RequestHolder
 * Package: com.gyc.mallchat.consumer.common.utils
 * Description: 请求上下文对象工具类
 *
 * @Author gyc
 * @Create 2024/4/10 21:20
 * @Version 1.0
 */
public class RequestHolder {

    private static final ThreadLocal<RequestInfo> threadLocal = new ThreadLocal<RequestInfo>();

    public static void set(RequestInfo requestInfo) {
        threadLocal.set(requestInfo);
    }

    public static RequestInfo get() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }
}
