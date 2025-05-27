package com.fruit.config;

import com.fruit.filter.RateLimiterFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Zhang-986
 * @date 2025/5/27
 */

@Configuration
public class FilterConfig implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Bean
    public FilterRegistrationBean<RateLimiterFilter> rateLimiterFilterRegistration(RateLimiterFilter filter) {
        FilterRegistrationBean<RateLimiterFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(filter);
        registration.addUrlPatterns("/user/getCode");
        registration.setName("rateLimiterFilter");
        registration.setOrder(1); // 设置过滤器顺序
        return registration;
    }

}