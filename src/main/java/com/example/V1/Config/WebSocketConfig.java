package com.example.V1.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * 绑定客户端url地址
 * WebSocket配置层
 */
@Configuration
public class WebSocketConfig implements WebSocketConfigurer {
    @Autowired
    private FrontBackWebSocketConfig webSocket;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocket, "/wsq/wfn/ckl").setAllowedOrigins("*");
    }
}
