package com.fruit.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Slf4j
@Component
public class RateLimiterFilter implements Filter {

    private final RedisTemplate<String, String> redisTemplate;
    private static final String[] IP_HEADER_CANDIDATES = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
    };

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String clientIp = getClientIpAddress(request);

        log.info("RateLimiterFilter; request from Client IP: {}", clientIp);

        // 限制每个IP每分钟只能获取一次验证码
        // redis处理限流
        String key = "rate_limit:" + clientIp;
        // 尝试获取key，如果存在，则说明在限制期内
        String existingTimestamp = redisTemplate.opsForValue().get(key);

        if (existingTimestamp != null) {
            // 可以选择返回一个错误响应，或者简单地记录并阻止请求
            log.warn("Rate limit exceeded for IP: {}", clientIp);
            servletResponse.getWriter().write("Rate limit exceeded. Please try again later.");
            // 注意：在实际应用中，应该返回一个标准的HTTP错误响应，例如429 Too Many Requests
            // ((HttpServletResponse) servletResponse).sendError(429, "Rate limit exceeded. Please try again later.");
            return; // 阻止请求继续
        }

        // 如果不存在，则设置新的时间戳，并允许请求通过
        Long currentTime = System.currentTimeMillis();
        // 设置60秒的过期时间
        redisTemplate.opsForValue().set(key, String.valueOf(currentTime), 60, TimeUnit.SECONDS);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getClientIpAddress(HttpServletRequest request) {
        log.debug("Attempting to determine client IP address...");
        for (String header : IP_HEADER_CANDIDATES) {
            String ipList = request.getHeader(header);
            if (StringUtils.hasText(ipList) && !"unknown".equalsIgnoreCase(ipList)) {
                log.debug("Found IP list in header '{}': {}", header, ipList);
                // X-Forwarded-For可能包含多个IP，第一个通常是客户端真实IP
                String[] ips = ipList.split(",");
                if (ips.length > 1) {
                    log.info("Multiple IPs found in header '{}': {}. Using the first one: {}", header, Arrays.toString(ips), ips[0].trim());
                } else {
                    log.debug("Single IP found in header '{}': {}", header, ips[0].trim());
                }
                return ips[0].trim();
            } else {
                log.trace("Header '{}' not found or is 'unknown'.", header);
            }
        }
        String remoteAddr = request.getRemoteAddr();
        log.debug("No IP found in headers, falling back to request.getRemoteAddr(): {}", remoteAddr);
        return remoteAddr;
    }

    @Override
    public void destroy() {

    }
}