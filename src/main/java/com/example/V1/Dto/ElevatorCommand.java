package com.example.V1.Dto;

import lombok.Data;

@Data
public class ElevatorCommand {
    private String elevatorId;
    private String command;
    private int floor;
}
