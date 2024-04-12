package com.gyc.mallchat.consumer.common.interceptor;

import com.gyc.mallchat.consumer.common.exception.enums.HttpErrorEnum;
import com.gyc.mallchat.consumer.user.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Optional;

/**
 * ClassName: TokenInterceptor
 * Package: com.gyc.mallchat.consumer.common.interceptor
 * Description:
 *
 * @Author gyc
 * @Create 2024/4/10 20:37
 * @Version 1.0
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String AUTHORIZATION_SCHEMA = "Bearer ";
    public static final String UID = "uid";

    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = getToken(request);
        Long validUid = loginService.getValidUid(token);
        if (Objects.nonNull(validUid)) {//token未过期
            //将用户uid存到request中，方便后续使用
            request.setAttribute(UID, validUid);
        } else {//用户未登录
            //判断接口的权限
            boolean isPublic = isPublicUri(request.getRequestURI());
            //不是公共接口
            if (!isPublic) {
                HttpErrorEnum.ACCESS_DENIED.sendHttpError(response);
            }
            return false;
        }
        return true;
    }

    /**
     * 接口权限判断
     *
     * @param requestURI
     * @return
     */
    private boolean isPublicUri(String requestURI) {
        String[] split = requestURI.split("/");
        return split.length > 3 && "public".equals(split[3]);
    }


    private String getToken(HttpServletRequest request) {
        String header = request.getHeader(HEADER_AUTHORIZATION);
        return Optional.ofNullable(header)
                .filter(h -> h.startsWith(AUTHORIZATION_SCHEMA))
                .map(h -> h.replaceFirst(AUTHORIZATION_SCHEMA, ""))
                .orElse(null);
    }
}
