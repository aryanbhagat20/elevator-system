package com.elevator.elevator_system.service;

import com.elevator.elevator_system.model.Direction;
import com.elevator.elevator_system.model.Elevator;
import com.elevator.elevator_system.model.ExternalRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElevatorSystemService {

    private final Scheduler scheduler;
    private final List<Elevator> elevators;

    public ElevatorSystemService() {
        this.scheduler = new BasicScheduler();

        Elevator elevator1 = new Elevator(1, 0);
        Elevator elevator2 = new Elevator(2, 5);

        scheduler.registerElevator(elevator1);
        scheduler.registerElevator(elevator2);

        this.elevators = List.of(elevator1, elevator2);
    }

    public void callElevator(int floor, Direction direction) {
        scheduler.submitExternalRequest(
                new ExternalRequest(floor, direction)
        );
    }

    public void stepSystem() {
        scheduler.step();
    }

    public List<Elevator> getElevators() {
        return elevators;
    }

    public void triggerEmergency(int elevatorId) {
        elevators.stream()
                .filter(e -> e.getElevatorId() == elevatorId)
                .findFirst()
                .ifPresent(Elevator::triggerEmergency);
    }

    public void setMaintenance(int elevatorId, boolean enable) {
        elevators.stream()
                .filter(e -> e.getElevatorId() == elevatorId)
                .findFirst()
                .ifPresent(e -> {
                    if (enable) e.enterMaintenance();
                    else e.exitMaintenance();
                });
    }
}
