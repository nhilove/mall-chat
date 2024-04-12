package com.gyc.mallchat.consumer.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: RedissonLock
 * Package: com.gyc.mallchat.consumer.common.annotation
 * Description:
 *
 * @Author gyc
 * @Create 2024/4/11 20:26
 * @Version 1.0
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.METHOD )
public @interface RedissonLock {

    /**
     * key的前缀，默认是方法的全限定名
     *
     * @return
     */
    String prefixKey() default "";

    /**
     * 支持EL表达式
     *
     * @return
     */
    String key();

    /**
     * 等待时间,默认-1不等待
     *
     * @return
     */
    int waitTime() default -1;

    /**
     * 时间单位，默认毫秒
     *
     * @return
     */
    TimeUnit unit() default TimeUnit.MILLISECONDS;
}
