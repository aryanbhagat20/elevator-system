package com.elevator.elevator_system.controller;

import com.elevator.elevator_system.model.Direction;
import com.elevator.elevator_system.service.ElevatorSystemService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class ElevatorWebSocketController {

    private final ElevatorSystemService service;

    public ElevatorWebSocketController(ElevatorSystemService service) {
        this.service = service;
    }

    @MessageMapping("/elevator/call")
    public void callElevator(Map<String, Object> request) {

        int targetFloor = (int) request.get("targetFloor");
        String directionStr = (String) request.get("direction");

        Direction direction = Direction.valueOf(directionStr);

        service.callElevator(targetFloor, direction);
    }

    @MessageMapping("/elevator/goto")
    public void sendElevatorToFloor(Map<String, Object> request) {

        int elevatorId = (int) request.get("elevatorId");
        int targetFloor = (int) request.get("targetFloor");

        service.sendElevatorToFloor(elevatorId, targetFloor);
    }
}
