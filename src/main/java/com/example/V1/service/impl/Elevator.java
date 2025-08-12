package com.example.V1.service.impl;

import com.example.V1.Dto.ElevatorCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;

@EnableScheduling
public class Elevator {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final Random random = new Random();
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private WebSocketSession session;

    private final String id ;
    private double currentFloor = 1.0;
    private int targetFloor = 1;
    private final int floorCount = 15;
    private String status = "停止";
    private String doorStatus = "关闭";
    private double speed = 0.0;
    private String direction = "无";
    private double loadWeight = 0.0;
    private final int maxWeight = 1000;
    private double temperature = 22.5;
    private String maintenanceStatus = "正常";
    private volatile boolean userControl = false;
    private volatile String mode = "AUTO";

    public Elevator(String id, WebSocketSession session) {
        this.id = id;
        this.session = session;
    }

    public void setWebSocketSession(WebSocketSession session) {
        this.session = session;
    }

    public void start() {
        executorService.scheduleAtFixedRate(this::simulateElevatorMovement, 0, 150, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        executorService.shutdownNow();
    }

    public void setTargetFloor(int floor) {
        this.userControl = true;
        this.targetFloor = floor;
        this.mode = "MANUAL";
    }

    private long doorOpenStartTime = 0; // 记录门打开的时间戳，单位毫秒

    private void simulateElevatorMovement() {
        try {
            // 模拟温度缓慢变化，基于前一次温度轻微浮动
            double tempDelta = (random.nextDouble() - 0.5) * 0.2; // 每次最多变化 ±0.1
            temperature += tempDelta;
            temperature = Math.max(20.0, Math.min(30.0, temperature)); // 限定温度在20~30之间
            temperature = Math.round(temperature * 10.0) / 10.0; // 保留1位小数


            if ("运行中".equals(status)) {
                double step = speed * 0.150;

                if ("上行".equals(direction)) {
                    currentFloor += step;
                    if (currentFloor >= targetFloor) {
                        currentFloor = targetFloor;
                        stopAtFloor();
                    } else {
                        updateRunningStatus("上行");
                    }
                } else { // 下行
                    currentFloor -= step;
                    if (currentFloor <= targetFloor) {
                        currentFloor = targetFloor;
                        stopAtFloor();
                    } else {
                        updateRunningStatus("下行");
                    }
                }
            } else if ("停止".equals(status)) {
                if ("打开".equals(doorStatus)) {
                    // 门刚打开，记录时间
                    if (doorOpenStartTime == 0) {
                        doorOpenStartTime = System.currentTimeMillis();
                    }
                    // 判断门开了多久，超过2秒就关门
                    else if (System.currentTimeMillis() - doorOpenStartTime >= 2000) {
                        doorStatus = "关闭";
                        loadWeight = random.nextInt(800);
                        doorOpenStartTime = 0; // 重置时间戳
                    }
                } else { // 门关闭状态
                    doorOpenStartTime = 0; // 确保时间戳重置
                    if (!userControl) {
                        // 自动模式，随机选择目标楼层
                        targetFloor = random.nextInt(floorCount) + 1;
                        if (targetFloor != (int) currentFloor) {
                            direction = targetFloor > currentFloor ? "上行" : "下行";
                            status = "运行中";
                            speed = 0.5;
                        }
                    } else {
                        // 用户控制模式
                        if (targetFloor != (int) currentFloor) {
                            direction = targetFloor > currentFloor ? "上行" : "下行";
                            status = "运行中";
                            speed = 0.8;
                        } else {
                            // 已在目标楼层，恢复自动模式
                            userControl = false;
                            mode = "AUTO";
                        }
                    }
                }
            }

            sendState();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void stopAtFloor() {
        status = "停止";
        direction = "无";
        speed = 0.0;
        doorStatus = "打开";
        userControl = false;
        mode = "AUTO";
    }

    private void updateRunningStatus(String dir) {
        status = "运行中";
        speed = 0.8;
        direction = dir;
        doorStatus = "关闭";
    }

    private synchronized void sendState() {
        try {
            if (session != null && session.isOpen()) {
                Map<String, Object> state = new HashMap<>();
                state.put("id", id);
                state.put("currentFloor", currentFloor);
                state.put("targetFloor", targetFloor);
                state.put("status", status);
                state.put("doorStatus", doorStatus);
                state.put("speed", speed);
                state.put("direction", direction);
                state.put("loadWeight", loadWeight);
                state.put("maxWeight", maxWeight);
                state.put("temperature", temperature);
                state.put("maintenanceStatus", maintenanceStatus);
                state.put("floorCount", floorCount);
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(state)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleCommand(ElevatorCommand command) {
        if (command == null){
            return;
        }

        String cmd = command.getCommand();
        int floor = command.getFloor();

        switch (cmd) {
            case "GOTO_FLOOR":
                setTargetFloor(floor);
                break;
            case "TOGGLE_DOOR":
                if (!"运行中".equals(status)) {
                    doorStatus = "打开".equals(doorStatus) ? "关闭" : "打开";
                }
                break;
            case "EMERGENCY_STOP":
                mode = "MANUAL";
                status = "已停止";
                speed = 0.0;
                break;
            case "RESUME_OPERATION":
                status = "停止";
                mode = "AUTO";
                break;
            default:
                System.out.println("未知命令: " + cmd);
        }
        sendState();
    }
}
