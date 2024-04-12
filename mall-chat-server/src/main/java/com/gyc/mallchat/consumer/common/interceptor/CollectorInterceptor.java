package com.gyc.mallchat.consumer.common.interceptor;

import cn.hutool.extra.servlet.ServletUtil;
import com.gyc.mallchat.consumer.common.domain.dto.RequestInfo;
import com.gyc.mallchat.consumer.common.utils.RequestHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * ClassName: CollectorInterceptor
 * Package: com.gyc.mallchat.consumer.common.interceptor
 * Description:
 *
 * @Author gyc
 * @Create 2024/4/10 21:16
 * @Version 1.0
 */
@Component
public class CollectorInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Long uid = Optional.ofNullable(request.getAttribute(TokenInterceptor.UID))
                .map(Object::toString)
                .map(Long::parseLong)
                .orElse(null);
        String ip = ServletUtil.getClientIP(request);
        RequestHolder.set(new RequestInfo(uid, ip));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        RequestHolder.remove();
    }
}
