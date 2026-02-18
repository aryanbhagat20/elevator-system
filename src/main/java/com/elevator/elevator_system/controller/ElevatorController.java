package com.elevator.elevator_system.controller;

import com.elevator.elevator_system.model.Direction;
import com.elevator.elevator_system.service.ElevatorSystemService;
import org.springframework.web.bind.annotation.*;
import com.elevator.elevator_system.dto.ElevatorStatusDTO;
import com.elevator.elevator_system.dto.CallElevatorRequestDTO;
import jakarta.validation.Valid;


import java.util.List;

@RestController
@RequestMapping("/api/elevator")
public class ElevatorController {

    private final ElevatorSystemService elevatorService;

    public ElevatorController(ElevatorSystemService elevatorService) {
        this.elevatorService = elevatorService;
    }

    // Call elevator from floor
    @PostMapping("/call")
    public String callElevator(@Valid @RequestBody CallElevatorRequestDTO request) {
        elevatorService.callElevator(
                request.getFloor(),
                Direction.valueOf(request.getDirection())
        );
        return "Elevator requested successfully";
    }

    // Get elevator status
    @GetMapping("/status")
    public List<ElevatorStatusDTO> getStatus() {
        return elevatorService.getElevatorStatus();
    }

    // Trigger emergency
    @PostMapping("/emergency")
    public String triggerEmergency(@RequestParam int elevatorId) {
        elevatorService.triggerEmergency(elevatorId);
        return "Emergency triggered for elevator " + elevatorId;
    }

    // Enable/disable maintenance
    @PostMapping("/maintenance")
    public String maintenance(@RequestParam int elevatorId,
                              @RequestParam boolean enable) {

        elevatorService.setMaintenance(elevatorId, enable);

        return enable
                ? "Elevator " + elevatorId + " in maintenance"
                : "Elevator " + elevatorId + " back to normal";
    }

    @PostMapping("/clearEmergency")
    public void clearEmergency(@RequestParam int elevatorId) {
        elevatorService.clearEmergency(elevatorId);
    }

    // Health check endpoint
    @GetMapping("/health")
    public String health() {
        return "ok";
    }

}
