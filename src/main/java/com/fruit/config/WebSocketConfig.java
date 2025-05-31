/*
package com.fruit.config;

import com.fruit.handler.UserHandler;
import com.fruit.interceptor.JwtWebSocketInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final UserHandler userHandler;
    private final JwtWebSocketInterceptor jwtInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 原生WebSocket端点（推荐）
        registry.addHandler(userHandler, "/ws/user")
                .addInterceptors(jwtInterceptor)
                .setAllowedOrigins("http://localhost:5173"); // 去掉.withSockJS()


    }
}*/
