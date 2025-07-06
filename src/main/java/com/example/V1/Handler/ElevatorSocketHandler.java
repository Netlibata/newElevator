package com.example.V1.Handler;

import com.example.V1.Dto.ElevatorCommand;
import com.example.V1.service.impl.Elevator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ElevatorSocketHandler extends TextWebSocketHandler {

    private final Map<String, Elevator> elevators = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        try {
            String elevatorId = extractElevatorId(session);
            if (elevatorId != null) {
                Elevator elevator = new Elevator(elevatorId, session);
                elevators.put(elevatorId, elevator);
                elevator.start();
                System.out.println("Elevator connected: " + elevatorId);
            } else {
                session.close(CloseStatus.BAD_DATA.withReason("Missing elevatorId"));
            }
        } catch (Exception e) {
            System.err.println("连接建立异常：" + e.getMessage());
            e.printStackTrace();
            try {
                session.close(CloseStatus.SERVER_ERROR.withReason("Internal server error"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String elevatorId = extractElevatorId(session);
        Elevator elevator = elevators.get(elevatorId);

        if (elevator != null) {
            ElevatorCommand command = objectMapper.readValue(message.getPayload(), ElevatorCommand.class);
            elevator.handleCommand(command);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String elevatorId = extractElevatorId(session);
        if (elevatorId != null) {
            Elevator elevator = elevators.remove(elevatorId);
            if (elevator != null) {
                elevator.stop();
            }
            System.out.println("Elevator disconnected: " + elevatorId);
        }
    }

    private String extractElevatorId(WebSocketSession session) {
        String path = session.getUri().getPath();
        return path.substring(path.lastIndexOf('/') + 1);
    }
}
