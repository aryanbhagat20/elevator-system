package com.elevator.elevator_system.service;

import com.elevator.elevator_system.model.Elevator;
import com.elevator.elevator_system.model.ExternalRequest;

import java.util.ArrayList;
import java.util.List;

public class BasicScheduler implements Scheduler {

    private final List<Elevator> elevators = new ArrayList<>();
    private final List<ExternalRequest> pendingRequests = new ArrayList<>();

    @Override
    public void registerElevator(Elevator elevator) {
        elevators.add(elevator);
    }

    @Override
    public void submitExternalRequest(ExternalRequest request) {
        pendingRequests.add(request);
    }

    @Override
    public void step() {
        if (pendingRequests.isEmpty()) {
            return;
        }

        ExternalRequest request = pendingRequests.get(0);

        Elevator bestElevator = null;
        int minDistance = Integer.MAX_VALUE;

        for (Elevator elevator : elevators) {
            if (!elevator.isIdle()) {
                continue;
            }

            int distance = Math.abs(
                    elevator.getCurrentFloor() - request.getFloor()
            );

            if (distance < minDistance) {
                minDistance = distance;
                bestElevator = elevator;
            }
        }

        if (bestElevator != null) {
            bestElevator.addDestination(request.getFloor());
            pendingRequests.remove(request);
        }

        // Let all elevators take one step
        for (Elevator elevator : elevators) {
            elevator.step();
        }
    }
}
