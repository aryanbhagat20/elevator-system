package com.elevator.elevator_system.service;

import com.elevator.elevator_system.model.*;

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

    // This method is called on every system tick to process pending requests and move elevators
    @Override
    public void step() {

        if (!pendingRequests.isEmpty()) {

            ExternalRequest request = pendingRequests.get(0);

            Elevator bestElevator = null;
            int minDistance = Integer.MAX_VALUE;

            for (Elevator elevator : elevators) {

                if (elevator.getMode() != ElevatorMode.NORMAL) {
                    continue;
                }

                int elevatorFloor = elevator.getCurrentFloor();
                int requestFloor = request.getFloor();

                boolean canServe = false;

                // Rule 1: Idle elevator can serve any request
                if (elevator.isIdle()) {
                    canServe = true;
                }

                // Rule 2: Moving UP
                else if (elevator.getMovementState() == MovementState.MOVING_UP) {
                    canServe =
                            request.getDirection() == Direction.UP &&
                                    requestFloor >= elevatorFloor;
                }

                // Rule 3: Moving DOWN
                else if (elevator.getMovementState() == MovementState.MOVING_DOWN) {
                    canServe =
                            request.getDirection() == Direction.DOWN &&
                                    requestFloor <= elevatorFloor;
                }

                if (!canServe) continue;

                int distance = Math.abs(elevatorFloor - requestFloor);

                if (distance < minDistance) {
                    minDistance = distance;
                    bestElevator = elevator;
                }
            }

            // If we found a suitable elevator, assign the request to it
            if (bestElevator != null) {
                bestElevator.addDestination(request.getFloor());
                pendingRequests.remove(request);
            }
        }

        // Let every elevator progress
        for (Elevator elevator : elevators) {
            elevator.step();
        }
    }
}
