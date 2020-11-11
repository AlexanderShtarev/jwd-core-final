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

    private static void setResult() {
        Random random = new Random();
        LOGGER.info("Updating mission");
        Optional<FlightMission> flightMission = MissionServiceImpl.MISSION_SERVICE.findMissionByCriteria(
                new FlightMissionCriteria.Builder() {{
                    missionResult(MissionResult.IN_PROGRESS);
                }}.build());
        if (flightMission.isPresent()) {
            int rnd = random.nextInt(6);
            if (rnd == 0) flightMission.get().setMissionResult(MissionResult.COMPLETED);
            if (rnd == 1) flightMission.get().setMissionResult(MissionResult.FAILED);
        }
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
                MissionResult missionResult;
                int result = scanner.nextInt();
                LOGGER.info("Finding Mission by Status");
                System.out.println(Arrays.toString(MissionResult.values()));
                System.out.println("Mission status(1,2,3,4,5):");
                missionResult = MissionResult.resolveMissionResultById(result);

                MissionResult finalMissionResult = missionResult;
                List<FlightMission> missionList = MissionServiceImpl.MISSION_SERVICE.findAllMissionsByCriteria(new FlightMissionCriteria.Builder() {{
                    missionResult(finalMissionResult);
                }}.build());
                if (missionList.isEmpty()) System.out.println("Missions not found");
                missionList.forEach(System.out::println);
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
