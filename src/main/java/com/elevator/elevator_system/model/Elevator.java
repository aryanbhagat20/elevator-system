package com.elevator.elevator_system.model;

import java.util.HashSet;
import java.util.TreeSet;
import java.util.Set;


public class Elevator {

    private final int elevatorId;
    private int currentFloor;

    private MovementState movementState;
    private DoorState doorState;
    private ElevatorMode mode;

    // Using TreeSet to keep destination floors sorted for easier nearest floor calculation
    private TreeSet<Integer> destinationFloors = new TreeSet<>();

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

    // Get the next destination floor based on current movement state and direction
    private Integer getNextDestination() {

        if (destinationFloors.isEmpty()) {
            return null;
        }

        if (movementState == MovementState.MOVING_UP || movementState == MovementState.IDLE) {
            Integer nextUp = destinationFloors.ceiling(currentFloor);
            if (nextUp != null) return nextUp;
            return destinationFloors.first(); // reverse later
        }

        if (movementState == MovementState.MOVING_DOWN) {
            Integer nextDown = destinationFloors.floor(currentFloor);
            if (nextDown != null) return nextDown;
            return destinationFloors.last(); // reverse later
        }

        return null;
    }

    // Elevator step function to be called by the scheduler on each tick
    public void step() {

        if (doorState == DoorState.OPEN) {
            closeDoor();
            return;
        }

        Integer target = getNextDestination();

        if (target == null) {
            movementState = MovementState.IDLE;
            return;
        }

        if (currentFloor < target) {
            movementState = MovementState.MOVING_UP;
            moveUp();
        }
        else if (currentFloor > target) {
            movementState = MovementState.MOVING_DOWN;
            moveDown();
        }
        else {
            openDoor();
            destinationFloors.remove(target);
        }
    }

    // Check if elevator is idle (not moving, doors closed, and in normal mode)
    public boolean isIdle() {
        return movementState == MovementState.IDLE
                && doorState == DoorState.CLOSED
                && mode == ElevatorMode.NORMAL;
    }
}
