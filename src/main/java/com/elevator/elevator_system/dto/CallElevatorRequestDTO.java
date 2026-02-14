package com.elevator.elevator_system.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CallElevatorRequestDTO {

    @Min(value = 0, message = "Floor must be >= 0")
    private int floor;

    @NotNull(message = "Direction is required")
    private String direction;

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
