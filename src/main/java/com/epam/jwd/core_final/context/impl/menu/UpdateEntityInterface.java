package com.epam.jwd.core_final.context.impl.menu;

import com.epam.jwd.core_final.context.UserInterface;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;
import com.epam.jwd.core_final.service.impl.MissionServiceImpl;
import com.epam.jwd.core_final.service.impl.SpaceshipServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.InputMismatchException;
import java.util.Optional;

import static com.epam.jwd.core_final.context.impl.menu.MainInterface.SCANNER;

public class UpdateEntityInterface implements UserInterface {
    static final UpdateEntityInterface UPDATE_ENTITY_INTERFACE = new UpdateEntityInterface();
    public static final Logger LOGGER = LoggerFactory.getLogger(UpdateEntityInterface.class);

    @Override
    public void show() {
        int input = -1;
        while (input != 0) {
            System.out.println("----------------------");
            System.out.println("Edit(Update):\n" +
                    "1.Update crew member details\n" +
                    "2.Update spaceship details\n" +
                    "3.Update mission details\n" +
                    "0.Back");
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
        switch (input) {
            case 1:
                LOGGER.info("Updating crew member details");
                System.out.println(CrewServiceImpl.CREW_SERVICE.findAllCrewMembers());
                System.out.println("Choose crew member(ID): ");
                Long crewMemberId = SCANNER.nextLong();
                Optional<CrewMember> crewMember = CrewServiceImpl.CREW_SERVICE.findCrewMemberByCriteria(
                        new CrewMemberCriteria.Builder() {{
                            id(crewMemberId);
                        }}.build());
                crewMember.ifPresent(CrewServiceImpl.CREW_SERVICE::updateCrewMemberDetails);
                if (crewMember.isEmpty()) System.out.println("Crew member with such id did not exist");
                break;
            case 2:
                LOGGER.info("Updating spaceship details");
                System.out.println(SpaceshipServiceImpl.SPACESHIP_SERVICE.findAllSpaceships());
                System.out.println("Choose spaceship(ID): ");
                Long spaceshipId = SCANNER.nextLong();
                Optional<Spaceship> spaceship = SpaceshipServiceImpl.SPACESHIP_SERVICE.findSpaceshipByCriteria(
                        new SpaceshipCriteria.Builder() {{
                            id(spaceshipId);
                        }}.build()
                );
                spaceship.ifPresent(SpaceshipServiceImpl.SPACESHIP_SERVICE::updateSpaceshipDetails);
                if (spaceship.isEmpty()) System.out.println("Spaceship with this id did not exist");
                break;
            case 3:
                LOGGER.info("Updating Mission Details");
                System.out.println(MissionServiceImpl.MISSION_SERVICE.findAllMissions());
                System.out.println("Choose Mission(ID): ");
                Long missionId = SCANNER.nextLong();
                Optional<FlightMission> flightMission = MissionServiceImpl.MISSION_SERVICE.findMissionByCriteria(
                        new FlightMissionCriteria.Builder() {{
                            id(missionId);
                        }}.build()
                );
                flightMission.ifPresent(MissionServiceImpl.MISSION_SERVICE::updateMissionDetails);
                if (flightMission.isEmpty()) System.out.println("Flight mission with this id did not exist");
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
