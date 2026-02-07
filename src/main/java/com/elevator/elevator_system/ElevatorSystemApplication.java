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

	@Bean
	CommandLineRunner bootstrapElevatorSystem() {
		return args -> {

			Scheduler scheduler = new BasicScheduler();

			Elevator elevator1 = new Elevator(1, 0); // Elevator 1 at floor 0
			Elevator elevator2 = new Elevator(2, 5); // Elevator 2 at floor 5

			// Register elevators
			scheduler.registerElevator(elevator1);
			scheduler.registerElevator(elevator2);

			// Simulate external requests
			scheduler.submitExternalRequest(
					new ExternalRequest(3, Direction.UP)
			);

			scheduler.submitExternalRequest(
					new ExternalRequest(7, Direction.DOWN)
			);

			// Simulate system ticks
			for (int i = 0; i < 10; i++) {
				scheduler.step();

				System.out.println(
						"Step " + i +
								" | Elevator-1 floor: " + elevator1.getCurrentFloor() +
								" | Elevator-2 floor: " + elevator2.getCurrentFloor()
				);
			}
		};
	}
}
