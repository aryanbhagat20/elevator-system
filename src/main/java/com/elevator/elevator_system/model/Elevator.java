package com.elevator.elevator_system.model;

import java.util.HashSet;
import java.util.Set;


public class Elevator {

    private final int elevatorId;
    private int currentFloor;

    private MovementState movementState;
    private DoorState doorState;
    private ElevatorMode mode;

    private Set<Integer> destinationFloors = new HashSet<>();

    // Constructor
    public Elevator(int elevatorId, int startFloor) {
        this.elevatorId = elevatorId;
        this.currentFloor = startFloor;

        // Safe initial states
        this.movementState = MovementState.IDLE;
        this.doorState = DoorState.CLOSED;
        this.mode = ElevatorMode.NORMAL;
    }

    // Getters (no setters yet on purpose)
    public int getElevatorId() {
        return elevatorId;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public MovementState getMovementState() {
        return movementState;
    }

    public DoorState getDoorState() {
        return doorState;
    }

    public ElevatorMode getMode() {
        return mode;
    }

    //Door operations 
    public boolean openDoor() {
        if (mode != ElevatorMode.NORMAL) {
            return false;
        }

        if (movementState != MovementState.IDLE) {
            return false;
        }

        if (doorState == DoorState.OPEN) {
            return false;
        }

        doorState = DoorState.OPEN;
        return true;
    }

    // Close door operation
    public boolean closeDoor() {
        if (doorState == DoorState.CLOSED) {
            return false;
        }

        doorState = DoorState.CLOSED;
        return true;
    }

    // Move elevator up by one floor
    public boolean moveUp() {
        if (mode != ElevatorMode.NORMAL) {
            return false;
        }

        if (doorState == DoorState.OPEN) {
            return false;
        }

        movementState = MovementState.MOVING_UP;
        currentFloor++;
        movementState = MovementState.IDLE;

        return true;
    }

    // Move down one floor
    public boolean moveDown() {
        if (mode != ElevatorMode.NORMAL) {
            return false;
        }

        if (doorState == DoorState.OPEN) {
            return false;
        }

        movementState = MovementState.MOVING_DOWN;
        currentFloor--;
        movementState = MovementState.IDLE;

        return true;
    }

    // Add a destination floor
    public boolean addDestination(int floor) {
        if (mode != ElevatorMode.NORMAL) {
            return false;
        }

        if (floor == currentFloor) {
            return false;
        }

        return destinationFloors.add(floor);
    }

    // Simple logic to get the nearest destination floor
    private Integer getNearestDestination() {
        return destinationFloors.stream()
                .min((a, b) -> Integer.compare(
                        Math.abs(a - currentFloor),
                        Math.abs(b - currentFloor)
                ))
                .orElse(null);
    }

    // Perform a step towards the nearest destination (Either move up, down, or open door)
    public void step() {
        // If door is open, close it first
        if (doorState == DoorState.OPEN) {
            closeDoor();
            return;
        }

        Integer target = getNearestDestination();

        if (target == null) {
            return;
        }

        if (currentFloor < target) {
            moveUp();
        } else if (currentFloor > target) {
            moveDown();
        } else {
            openDoor();
            destinationFloors.remove(target);
        }
    }
}
