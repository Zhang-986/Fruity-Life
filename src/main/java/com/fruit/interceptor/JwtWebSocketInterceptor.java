package com.fruit.interceptor;

import com.fruit.utils.JwtUtil;
import com.fruit.utils.UserContext;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.net.URLDecoder;
import java.util.Map;

@Component
public class JwtWebSocketInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response,
                                   WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {

        // 从URL参数获取token
        String token = getTokenFromRequest(request);

        if (token == null) {
            return false; // 拒绝握手
        }

        try {
            if (!JwtUtil.validateToken(token)) {
                return false; // Token验证失败
            }
        } catch (Exception e) {
            return false; // Token验证异常
        }

        // 提取用户信息并存储到WebSocket session属性中
        String userId = JwtUtil.extractUsername(token);
        UserContext.setUserId(Long.valueOf(userId));

        return true; // 允许握手
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
        // 握手后处理，保持空实现
    }

    private String getTokenFromRequest(ServerHttpRequest request) {
        String query = request.getURI().getQuery();

        if (query != null && query.contains("token=")) {
            String token = query.split("token=")[1].split("&")[0];
            try {
                return URLDecoder.decode(token, "UTF-8");
            } catch (Exception e) {
                return token; // 解码失败返回原始token
            }
        }
        return null;
    }
}