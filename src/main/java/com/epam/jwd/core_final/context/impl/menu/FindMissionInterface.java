package com.epam.jwd.core_final.context.impl.menu;

import com.epam.jwd.core_final.context.UserInterface;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.service.impl.MissionServiceImpl;
import com.epam.jwd.core_final.service.impl.SpaceshipServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.*;

import static com.epam.jwd.core_final.context.impl.menu.MainInterface.SCANNER;

public class FindMissionInterface implements UserInterface {
    static final FindMissionInterface FIND_MISSION_INTERFACE = new FindMissionInterface();
    private static final Logger LOGGER = LoggerFactory.getLogger(FindMissionInterface.class);

    @Override
    public void show() {
        LOGGER.info("Find mission interface");
        setResult();
        int input = -1;
        while (input != 0) {
            System.out.println("----------------------");
            System.out.println("Find Mission:\n" +
                    "1.Find Mission by Name\n" +
                    "2.Find Mission by Id\n" +
                    "3.Find Mission by Mission Status\n" +
                    "4.Find Mission by Assigned Ship\n" +
                    "5.Find ALL Missions\n" +
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

    private static FlightMission setResult() {
        Random random = new Random();
        if (MissionServiceImpl.MISSION_SERVICE.findAllMissions().isEmpty()) {
            LOGGER.info("Update mission - mission list is empty");
            return null;
        } else {
            LOGGER.info("Updating mission");
            int rand = random.nextInt(NassaContext.NASSA_CONTEXT.retrieveBaseEntityList(FlightMission.class).size());
            System.out.println(rand);
            Optional<FlightMission> flightMission = MissionServiceImpl.MISSION_SERVICE.findMissionByCriteria(
                    new FlightMissionCriteria.Builder() {{
                        id((long) rand);
                    }}.build());
            if (flightMission.isEmpty()) return null;
            if ((flightMission.get().getMissionResult() != MissionResult.FAILED)
                    && (flightMission.get().getMissionResult() != MissionResult.CANCELLED)
                    && (flightMission.get().getMissionResult() != MissionResult.COMPLETED)
                    && (flightMission.get().getMissionResult() != MissionResult.PLANNED)) {
                if (rand < NassaContext.NASSA_CONTEXT.retrieveBaseEntityList(CrewMember.class).size()) {
                    int newRand = random.nextInt(3);
                    if (newRand == 0) flightMission.get().setMissionResult(MissionResult.FAILED);
                    if (newRand == 1) flightMission.get().setMissionResult(MissionResult.COMPLETED);
                    if (newRand == 2) flightMission.get().setMissionResult(MissionResult.IN_PROGRESS);
                    flightMission.get().setEndDate(LocalDateTime.now());
                }
            }
        }
        return null;
    }

    private void handleInput(int input) {
        Scanner scanner = new Scanner(System.in);
        Optional<FlightMission> flightMission;
        switch (input) {
            case 1:
                LOGGER.info("Finding Mission by Name");
                System.out.println("Mission Name: ");
                String name = scanner.nextLine();
                flightMission = MissionServiceImpl.MISSION_SERVICE.findMissionByCriteria(new FlightMissionCriteria.Builder() {{
                    name(name);
                }}.build());
                flightMission.ifPresent(mission -> System.out.println(mission.toString()));
                if (flightMission.isEmpty()) System.out.println("Mission not found");
                break;
            case 2:
                LOGGER.info("Finding Mission by id");
                System.out.println("Mission id: ");
                Long id = scanner.nextLong();
                flightMission = MissionServiceImpl.MISSION_SERVICE.findMissionByCriteria(new FlightMissionCriteria.Builder() {{
                    id(id);
                }}.build());
                flightMission.ifPresent(flightMission1 -> System.out.println(flightMission.get().toString()));
                if (flightMission.isEmpty()) System.out.println("Mission not found");
                break;
            case 3:
                LOGGER.info("Finding Mission by Status");
                System.out.println(Arrays.toString(MissionResult.values()));
                System.out.println("Mission status(1,2,3,4,5):");
                int result = scanner.nextInt();
                MissionResult missionResult = null;
                while ((result < 1) || (result > 5)) {
                    result = scanner.nextInt();
                    if (result == 1) missionResult = MissionResult.PLANNED;
                    if (result == 2) missionResult = MissionResult.IN_PROGRESS;
                    if (result == 3) missionResult = MissionResult.CANCELLED;
                    if (result == 4) missionResult = MissionResult.COMPLETED;
                    if (result == 5) missionResult = MissionResult.FAILED;
                }

                MissionResult finalMissionResult = missionResult;
                List<FlightMission> flightMissionList = MissionServiceImpl.MISSION_SERVICE.findAllMissionsByCriteria(new FlightMissionCriteria.Builder() {{
                    missionResult(finalMissionResult);
                }}.build());
                if (flightMissionList.isEmpty()) {
                    System.out.println("Mission not found");
                } else {
                    flightMissionList.forEach(System.out::println);
                }
                break;
            case 4:
                LOGGER.info("Finding Mission by assigned Ship");
                System.out.println("Assigned Ship id: ");
                Long assignedShipId = scanner.nextLong();
                Optional<Spaceship> spaceship = SpaceshipServiceImpl.SPACESHIP_SERVICE.findSpaceshipByCriteria(new SpaceshipCriteria.Builder() {{
                    id(assignedShipId);
                }}.build());
                if (spaceship.isPresent()) {
                    flightMission = MissionServiceImpl.MISSION_SERVICE.findMissionByCriteria(new FlightMissionCriteria.Builder() {{
                        assignedSpaceship(spaceship.get());
                    }}.build());
                    flightMission.ifPresent(System.out::println);
                    if (flightMission.isEmpty()) System.out.println("Mission not found");
                }
                if (spaceship.isEmpty()) System.out.println("Spaceship did not exist");
                break;
            case 5:
                LOGGER.info("Finding All Missions");
                System.out.println(MissionServiceImpl.MISSION_SERVICE.findAllMissions());
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
