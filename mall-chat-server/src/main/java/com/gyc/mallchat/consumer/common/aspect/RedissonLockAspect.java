package com.gyc.mallchat.consumer.common.aspect;

import com.gyc.mallchat.consumer.common.annotation.RedissonLock;
import com.gyc.mallchat.consumer.common.service.LockService;
import com.gyc.mallchat.consumer.common.utils.SpElUtils;
import jodd.util.StringUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * ClassName: RedissonLockAspect
 * Package: com.gyc.mallchat.consumer.common.aspect
 * Description:
 *
 * @Author gyc
 * @Create 2024/4/11 20:55
 * @Version 1.0
 */
@Component
@Aspect
@Order( 0 )
public class RedissonLockAspect {

    @Autowired
    private LockService lockService;

    @Around( "@annotation(redissonLock)" )
    public Object around(ProceedingJoinPoint joinPoint, RedissonLock redissonLock) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String prefix = StringUtil.isBlank(redissonLock.prefixKey()) ? SpElUtils.getMethodKey(method) : redissonLock.prefixKey();
        String key = SpElUtils.parseSpEl(method, joinPoint.getArgs(), redissonLock.key());
        return lockService.executeWithLock(prefix + ":" + key, redissonLock.waitTime(), redissonLock.unit(), joinPoint::proceed);
    }

}
