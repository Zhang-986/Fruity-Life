package com.fruit.config;

import com.fruit.config.properties.ExcludeProperties;
import com.fruit.interceptor.JwtInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zhang-986
 * @date 2025/5/27
 */
@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(ExcludeProperties.class)
public class WebMvcConfig implements Serializable, WebMvcConfigurer {
    private final JwtInterceptor jwtInterceptor;
    private final ExcludeProperties excludeProperties;
    @Serial
    private static final long serialVersionUID = 1L;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> user = excludeProperties.getUser();
        List<String> publicApi = excludeProperties.getPublicApi();
        List<String> swagger = excludeProperties.getSwagger();
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**") // 拦截所有请求
                .excludePathPatterns(user)
                .excludePathPatterns(publicApi)
                .excludePathPatterns(swagger);
    }
}