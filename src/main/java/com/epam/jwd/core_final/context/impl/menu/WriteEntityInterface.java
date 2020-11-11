package com.epam.jwd.core_final.context.impl.menu;

import com.epam.jwd.core_final.context.UserInterface;
import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;
import com.epam.jwd.core_final.service.impl.MissionServiceImpl;
import com.epam.jwd.core_final.service.impl.SpaceshipServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;

import static com.epam.jwd.core_final.context.impl.menu.MainInterface.SCANNER;

public class WriteEntityInterface implements UserInterface {
    static final WriteEntityInterface WRITE_ENTITY_INTERFACE = new WriteEntityInterface();
    private static final Logger LOGGER = LoggerFactory.getLogger(WriteEntityInterface.class);

    @Override
    public void show() {
        int input = -1;
        while (input != 0) {
            System.out.println("----------------------");
            System.out.println("Write To File:\n" +
                    "1.Write crew members\n" +
                    "2.Write spaceships\n" +
                    "3.Write missions\n" +
                    "0.Back ");
            System.out.println("----------------------");
            System.out.print("Please, enter a number: ");
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
        String outputPath = ApplicationProperties.APPLICATION_PROPERTIES.getOutputRootDir();
        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        switch (input) {
            case 1:
                LOGGER.info("Writing crew members to file");
                List<CrewMember> crewMembers = CrewServiceImpl.CREW_SERVICE.findAllCrewMembers();
                try (FileOutputStream fileOutputStream = new FileOutputStream(outputPath + "crewmember.json")) {
                    objectMapper.writeValue(fileOutputStream, crewMembers);
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                    e.printStackTrace();
                }
                LOGGER.info("Crew members were written to a file");
                System.out.println("Finished");
                break;
            case 2:
                LOGGER.info("Writing spaceships to file");
                List<Spaceship> spaceships = SpaceshipServiceImpl.SPACESHIP_SERVICE.findAllSpaceships();
                try (FileOutputStream fileOutputStream = new FileOutputStream(outputPath + "spaceship.json")) {
                    objectMapper.writeValue(fileOutputStream, spaceships);
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                    e.printStackTrace();
                }
                LOGGER.info("Spaceships were written to a file");
                System.out.println("Finished");
                break;
            case 3:
                LOGGER.info("Writing crew members to a file");
                List<FlightMission> flightMissions = MissionServiceImpl.MISSION_SERVICE.findAllMissions();
                try (FileOutputStream fileOutputStream = new FileOutputStream(outputPath + "mission.json")) {
                    objectMapper.writeValue(fileOutputStream, flightMissions);
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                    e.printStackTrace();
                }
                LOGGER.info("Missions were written to a file");
                System.out.println("Finished");
                break;
            case 0:
                break;
            default:
                LOGGER.warn("Unexpected value");
                System.out.println("----------------------");
                System.out.println("Unexpected value: " + input);
                break;
        }
    }
}
