package com.fruit.interceptor;

import cn.hutool.jwt.JWTUtil;
import com.fruit.utils.JwtUtil;
import com.fruit.utils.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Zhang-986
 * @date 2025/5/28
 */

@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.获取token
        String token = request.getHeader("token");
        // 2.判断token是否存在
        if (token == null || token.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 设置未授权状态码
            return false; // 阻止请求继续处理
        }
        // 3.验证token的有效性
        Boolean flag = JwtUtil.validateToken(token);
        if (!flag) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 设置未授权状态码
            return false; // 阻止请求继续处理
        }
        log.info("{}", token);
        // 4.将token中的用户信息存入请求属性中，供后续处理使用
        String userId = JwtUtil.extractUsername(token);
        log.info("{}", userId);
        UserContext.setUserId(Long.valueOf(userId));
        // 5.允许请求继续处理
        return true;
    }


}