package com.elevator.elevator_system.model;

public class ExternalRequest {

    private final int floor;
    private final Direction direction;

    // For simplicity, we can track how long the request has been waiting to help with scheduling decisions
    private int waitTime = 0;

    // This threshold can be used by the scheduler to prioritize requests that have been waiting too long
    private static final int STARVATION_THRESHOLD = 5;

    public ExternalRequest(int floor, Direction direction) {
        this.floor = floor;
        this.direction = direction;
    }

    public int getFloor() {
        return floor;
    }

    public Direction getDirection() {
        return direction;
    }

    public void incrementWaitTime() {
        waitTime++;
    }

    public int getWaitTime() {
        return waitTime;
    }

}
