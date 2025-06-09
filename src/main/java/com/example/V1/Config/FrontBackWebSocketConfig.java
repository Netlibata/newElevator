package com.example.V1.Config;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * ws控制器
 * 处理前端和后端的socket通信
 * ws配置文件
 * @author Netlibata
 */
@Component
public class FrontBackWebSocketConfig extends TextWebSocketHandler {
    // 保存所有连接的客户端
    private static final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    /**
     * 连接客户端
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("客户端连接成功");
    }

    /**
     * 接收客户端消息
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("接收到客户端消息：" + message.getPayload());
    }

    /**
     * 自动断开连接
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        //当客户端连接关闭时，会从列表中移除对应的Session对象（客户端）
        sessions.remove(session);
        System.out.println("客户端断开连接");
    }

    /**
     * 像客户端发送消息
     * 通过遍历所有建立的WebSocketSession对象并发送消息
     */

    public  void sendMessage(String message) {
        for (WebSocketSession webSocketSession : sessions) {
            try {
                //创建TextMessage对象并发送消息
                webSocketSession.sendMessage(new TextMessage(message));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
