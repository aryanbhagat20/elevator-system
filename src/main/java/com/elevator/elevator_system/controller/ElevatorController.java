package com.elevator.elevator_system.controller;

import com.elevator.elevator_system.model.Direction;
import com.elevator.elevator_system.model.Elevator;
import com.elevator.elevator_system.service.ElevatorSystemService;
import org.springframework.web.bind.annotation.*;

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
    public String callElevator(@RequestParam int floor,
                               @RequestParam Direction direction) {

        elevatorService.callElevator(floor, direction);
        return "Elevator requested successfully";
    }

    // Get elevator status
    @GetMapping("/status")
    public List<Elevator> getStatus() {
        return elevatorService.getElevators();
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
}
