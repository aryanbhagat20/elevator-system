package com.elevator.elevator_system.service;

import com.elevator.elevator_system.model.Elevator;
import com.elevator.elevator_system.model.ExternalRequest;

public interface Scheduler {

    void registerElevator(Elevator elevator);

    void submitExternalRequest(ExternalRequest request);

    void step();
}
