package com.gyc.mallchat.consumer.common.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * ClassName: MyUncaughtExceptionHandler
 * Package: com.gyc.mallchat.consumer.common.thread
 * Description:
 *
 * @Author gyc
 * @Create 2024/4/10 11:20
 * @Version 1.0
 */
@Slf4j
public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    /**
     * 捕获异常，打印日志
     *
     * @param t the thread
     * @param e the exception
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.error("Thread exception ", e);
    }
}
