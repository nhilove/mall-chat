package com.gyc.mallchat.consumer.common.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ClassName: InterceptorConfig
 * Package: com.gyc.mallchat.consumer.common.interceptor
 * Description:
 *
 * @Author gyc
 * @Create 2024/4/10 20:55
 * @Version 1.0
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Autowired
    private CollectorInterceptor collectorInterceptor;

    @Autowired
    private BlackInterceptor blackInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/capi/**");
        registry.addInterceptor(collectorInterceptor)
                .addPathPatterns("/capi/**");
        registry.addInterceptor(blackInterceptor)
                .addPathPatterns("/capi/**");
    }
}
