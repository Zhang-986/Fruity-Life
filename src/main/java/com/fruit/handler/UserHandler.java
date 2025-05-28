package com.fruit.handler;

import com.fruit.result.R;
import com.fruit.service.IGuestSessions;
import com.fruit.utils.UserContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Zhang-986
 * @date 2025/5/28
 */

@Slf4j
@RequiredArgsConstructor
@Component
public class UserHandler implements WebSocketHandler, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final IGuestSessions iGuestSessions;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("用户 {} 成功连接WebSocket", UserContext.getUserId());

        // 将用户ID存储到WebSocket会话属性中
        Long userId = UserContext.getUserId();
        session.getAttributes().put("userId", userId);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        // 从会话属性中获取用户ID
        Long userId = (Long) session.getAttributes().get("userId");

        // 重新设置到当前线程的ThreadLocal中，以便服务方法可以使用
        UserContext.setUserId(userId);

        // 处理接收到的消息
        Boolean flag = iGuestSessions.isCompleted();

        // 根据用户完善信息状态发送不同的消息
        TextMessage responseMessage;
        if (!flag) {
            log.info("用户 {} 未完善个人信息，发送提示码: 8888", userId);
            responseMessage = new TextMessage("8888");
        } else {
            log.info("用户 {} 已完善个人信息，发送确认码: 666", userId);
            responseMessage = new TextMessage("666");
        }

        // 发送消息给客户端
        try {
            session.sendMessage(responseMessage);
        } catch (Exception e) {
            log.error("向用户 {} 发送WebSocket消息失败: {}", userId, e.getMessage());
        }

        // 使用完毕后清理当前线程的ThreadLocal
        UserContext.clear();
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        UserContext.clear();
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}