package com.elevator.elevator_system;

import com.elevator.elevator_system.model.Direction;
import com.elevator.elevator_system.model.Elevator;
import com.elevator.elevator_system.model.ExternalRequest;
import com.elevator.elevator_system.service.BasicScheduler;
import com.elevator.elevator_system.service.Scheduler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ElevatorSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElevatorSystemApplication.class, args);
	}
}
