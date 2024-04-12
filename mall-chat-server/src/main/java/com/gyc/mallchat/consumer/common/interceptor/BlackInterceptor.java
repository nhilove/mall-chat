package com.gyc.mallchat.consumer.common.interceptor;

import cn.hutool.extra.servlet.ServletUtil;
import com.gyc.mallchat.consumer.common.domain.dto.RequestInfo;
import com.gyc.mallchat.consumer.common.exception.enums.HttpErrorEnum;
import com.gyc.mallchat.consumer.common.utils.RequestHolder;
import com.gyc.mallchat.consumer.user.domain.enums.BlackTypeEnum;
import com.gyc.mallchat.consumer.user.service.cache.UserCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

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
public class BlackInterceptor implements HandlerInterceptor {

    @Autowired
    private UserCache userCache;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<Integer, Set<String>> blackUserMap = userCache.getBlackUserMap();
        RequestInfo requestInfo = RequestHolder.get();
        if (inBlackList(requestInfo.getUid(), blackUserMap.get(BlackTypeEnum.UID.getType()))) {
            HttpErrorEnum.ACCESS_DENIED.sendHttpError(response);
            return false;
        }
        if (inBlackList(requestInfo.getIp(), blackUserMap.get(BlackTypeEnum.IP.getType()))) {
            HttpErrorEnum.ACCESS_DENIED.sendHttpError(response);
            return false;
        }
        return true;
    }

    private boolean inBlackList(Object target, Set<String> set) {
        if (Objects.isNull(target) || CollectionUtils.isEmpty(set)) {
            return false;
        }
        return set.contains(target.toString());
    }



}
