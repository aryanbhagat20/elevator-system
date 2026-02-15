package com.elevator.elevator_system.service;

import com.elevator.elevator_system.dto.ElevatorStatusDTO;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ElevatorEngine {

    private final ElevatorSystemService elevatorSystemService;
    private final SimpMessagingTemplate messagingTemplate;

    public ElevatorEngine(ElevatorSystemService elevatorSystemService,
                          SimpMessagingTemplate messagingTemplate) {

        this.elevatorSystemService = elevatorSystemService;
        this.messagingTemplate = messagingTemplate;
    }

    @Scheduled(fixedRate = 1000)
    public void tick() {

        elevatorSystemService.stepSystem();

        List<ElevatorStatusDTO> status =
                elevatorSystemService.getElevatorStatus();

        messagingTemplate.convertAndSend("/topic/elevators", status);
    }
}
