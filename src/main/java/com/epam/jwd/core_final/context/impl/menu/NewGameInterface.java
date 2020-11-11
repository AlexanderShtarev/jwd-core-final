package com.epam.jwd.core_final.context.impl.menu;

import com.epam.jwd.core_final.context.UserInterface;
import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;
import com.epam.jwd.core_final.service.impl.MissionServiceImpl;
import com.epam.jwd.core_final.service.impl.SpaceshipServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.*;

import static com.epam.jwd.core_final.context.impl.menu.MainInterface.SCANNER;

public class NewGameInterface implements UserInterface {
    static final NewGameInterface NEW_GAME_INTERFACE = new NewGameInterface();
    private static final Logger LOGGER = LoggerFactory.getLogger(NewGameInterface.class);

    @Override
    public void show() {
        int input = -1;
        while (input != 0) {
            System.out.println("----------------------");
            System.out.println("New Game:\n" +
                    "1.Start Mission\n" +
                    "0.Break");
            System.out.println("----------------------");
            System.out.print("Please, Enter a Number: ");
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
        Scanner scanner = new Scanner(System.in);
        switch (input) {
            case 1:
                LOGGER.info("Starting Mission");
                if (MissionServiceImpl.MISSION_SERVICE.findAllMissions().isEmpty()) {
                    System.out.println("You do not have any available missions");
                    break;
                }
                System.out.println("Enter mission name:");
                FlightMission flightMission = null;
                String name = null;
                while (name == null) {
                    name = scanner.nextLine();
                    String finalName = name;
                    Optional<FlightMission> optionalFlightMission = MissionServiceImpl.MISSION_SERVICE.findMissionByCriteria(
                            new FlightMissionCriteria.Builder() {{
                                name(finalName);
                            }}.build());
                    if (optionalFlightMission.isEmpty()) {
                        name = null;
                        System.out.println("Mission does not exists");
                    } else {
                        flightMission = optionalFlightMission.get();
                    }
                }

                if (flightMission.getMissionResult() != MissionResult.PLANNED) {
                    System.out.println("Mission already started");
                    break;
                }

                try {
                    SpaceshipServiceImpl.SPACESHIP_SERVICE.assignSpaceshipOnMission(flightMission);
                    LOGGER.info("Spaceship assigned");
                    System.out.println("Spaceship " + flightMission.getAssignedSpaceShip().getName() + " was assigned");
                    CrewServiceImpl.CREW_SERVICE.assignCrewMembersOnMission(flightMission);
                    LOGGER.info("Crew assigned");
                } catch (UnknownEntityException ex) {
                    LOGGER.error("Available ship not found");
                    System.out.println("There is no available spaceships for this mission");
                    break;
                } catch (IllegalArgumentException ex) {
                    break;
                }
                flightMission.setMissionResult(MissionResult.IN_PROGRESS);
                flightMission.setStartDate(LocalDateTime.now());
                break;
            case 0:
                break;
            default:
                LOGGER.warn("Unexpected Value");
                System.out.println("----------------------");
                System.out.println("Unexpected value: " + input);
                break;
        }
    }
}
