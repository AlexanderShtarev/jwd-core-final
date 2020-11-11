package com.epam.jwd.core_final.context.impl.menu;

import com.epam.jwd.core_final.context.UserInterface;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.service.impl.SpaceshipServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

import static com.epam.jwd.core_final.context.impl.menu.MainInterface.SCANNER;

public class FindShipInterface implements UserInterface {
    static final FindShipInterface FIND_SHIP_INTERFACE = new FindShipInterface();
    private static final Logger LOGGER = LoggerFactory.getLogger(FindShipInterface.class);

    @Override
    public void show() {
        LOGGER.info("Find spaceship interface");
        int input = -1;
        while (input != 0) {
            System.out.println("----------------------");
            System.out.println("Find Spaceship:\n" +
                    "1.Find Spaceship by Name\n" +
                    "2.Find Spaceship by Id\n" +
                    "3.Find Spaceship by Flight Distance\n" +
                    "4.Find Spaceship by Flight Distance and Mission Readiness\n" +
                    "5.Find ALL Spaceships\n" +
                    "0.Back ");
            System.out.println("----------------------");
            System.out.print("Please Enter a Number: ");
            try {
                input = SCANNER.nextInt();
                handleInput(input);
            } catch (InputMismatchException ex) {
                LOGGER.error("Input mismatch");
                System.out.println("Error: Incorrect input");
                SCANNER.nextLine();
            }
        }
    }

    private void handleInput(int input) {
        Optional<Spaceship> spaceship;
        Scanner scanner = new Scanner(System.in);
        switch (input) {
            case 1:
                LOGGER.info("Finding Spaceship by Name");
                System.out.println("Spaceship name: ");
                String name = scanner.nextLine();
                spaceship = SpaceshipServiceImpl.SPACESHIP_SERVICE.findSpaceshipByCriteria(new SpaceshipCriteria.Builder() {{
                    name(name);
                }}.build());
                spaceship.ifPresent(spaceship1 -> System.out.println(spaceship.get().toString()));
                if (spaceship.isEmpty()) System.out.println("Spaceship not found");
                break;
            case 2:
                LOGGER.info("Finding Spaceship by Id");
                System.out.println("Spaceship id: ");
                Long id = scanner.nextLong();
                spaceship = SpaceshipServiceImpl.SPACESHIP_SERVICE.findSpaceshipByCriteria(new SpaceshipCriteria.Builder() {{
                    id(id);
                }}.build());
                spaceship.ifPresent(spaceship1 -> System.out.println(spaceship.get().toString()));
                if (spaceship.isEmpty()) System.out.println("Spaceship not found");
                break;
            case 3:
                LOGGER.info("Finding Spaceship by Flight Distance");
                System.out.println("Spaceship flight distance: ");
                Long flightDistance = scanner.nextLong();
                System.out.println("----------------------");
                spaceship = SpaceshipServiceImpl.SPACESHIP_SERVICE.findSpaceshipByCriteria(new SpaceshipCriteria.Builder() {{
                    flightDistance(flightDistance);
                }}.build());
                spaceship.ifPresent(spaceship1 -> System.out.println(spaceship.get().toString()));
                if (spaceship.isEmpty()) System.out.println("Spaceship not found");
                break;
            case 4:
                LOGGER.info("Finding Spaceship by Flight Distance and Readiness");
                System.out.println("Spaceship flight distance: ");
                Long flightDistance1 = scanner.nextLong();
                System.out.println("----------------------");
                System.out.println("Spaceship readiness(true,false): ");
                Boolean readiness = scanner.nextBoolean();
                System.out.println("----------------------");
                spaceship = SpaceshipServiceImpl.SPACESHIP_SERVICE.findAllSpaceships().stream()
                        .filter(spaceship1 -> spaceship1.getFlightDistance() > flightDistance1)
                        .filter(Spaceship::getReadyForNextMissions)
                        .findAny();
                spaceship.ifPresent(spaceship1 -> System.out.println(spaceship.get().toString()));
                if (spaceship.isEmpty()) System.out.println("Spaceship not found");
                break;
            case 5:
                LOGGER.info("Finding All Spaceships");
                System.out.println(SpaceshipServiceImpl.SPACESHIP_SERVICE.findAllSpaceships());
                System.out.println("----------------------");
                break;
            case 0:
                break;
            default:
                LOGGER.warn("Unexpected Value: ");
                System.out.println("----------------------");
                System.out.println("Unexpected value: " + input);
                System.out.println("----------------------");
                break;
        }
    }
}
