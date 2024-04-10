package com.gyc.mallchat.consumer.common.thread;

import lombok.AllArgsConstructor;

import java.util.concurrent.ThreadFactory;

/**
 * ClassName: MyThreadFactory
 * Package: com.gyc.mallchat.consumer.common.thread
 * Description:
 *
 * @Author gyc
 * @Create 2024/4/10 11:22
 * @Version 1.0
 */
@AllArgsConstructor
public class MyThreadFactory implements ThreadFactory {


    public static final MyUncaughtExceptionHandler UNCAUGHT_EXCEPTION_HANDLER = new MyUncaughtExceptionHandler();
    private ThreadFactory original;

    /**
     * 使用装饰器模式，额外添加threadFactory的功能
     */
    @Override
    public Thread newThread(Runnable r) {
        //保留原始thread工厂的功能
        Thread thread = original.newThread(r);
        //装饰自己需要的功能
        thread.setUncaughtExceptionHandler(UNCAUGHT_EXCEPTION_HANDLER);
        return thread;

    }
}
