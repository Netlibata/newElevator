package com.example.V1.config;
import com.example.V1.Handler.ElevatorSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    public ElevatorSocketHandler elevatorSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(elevatorSocketHandler, "/ws/elevator/status/**")
                .setAllowedOrigins("*"); // 允许跨域（前端连接用）
    }
}
