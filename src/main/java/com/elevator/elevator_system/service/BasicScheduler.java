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
        // Logic will come in next phase
    }
}
