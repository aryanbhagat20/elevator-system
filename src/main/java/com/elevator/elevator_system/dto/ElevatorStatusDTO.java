package com.elevator.elevator_system.dto;

public class ElevatorStatusDTO {

    private int elevatorId;
    private int currentFloor;
    private String movementState;
    private String doorState;
    private String mode;

    public ElevatorStatusDTO(int elevatorId,
                             int currentFloor,
                             String movementState,
                             String doorState,
                             String mode) {

        this.elevatorId = elevatorId;
        this.currentFloor = currentFloor;
        this.movementState = movementState;
        this.doorState = doorState;
        this.mode = mode;
    }

    public int getElevatorId() {
        return elevatorId;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public String getMovementState() {
        return movementState;
    }

    public String getDoorState() {
        return doorState;
    }

    public String getMode() {
        return mode;
    }
}
