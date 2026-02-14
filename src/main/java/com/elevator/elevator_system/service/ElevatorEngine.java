package com.elevator.elevator_system.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ElevatorEngine {

    private final ElevatorSystemService elevatorSystemService;

    public ElevatorEngine(ElevatorSystemService elevatorSystemService) {
        this.elevatorSystemService = elevatorSystemService;
    }

    @Scheduled(fixedRate = 1000)
    public void tick() {
        elevatorSystemService.stepSystem();
    }
}
