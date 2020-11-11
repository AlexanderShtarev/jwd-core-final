package com.epam.jwd.core_final.context.impl.menu;

import com.epam.jwd.core_final.context.UserInterface;
import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;
import com.epam.jwd.core_final.service.impl.MissionServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static com.epam.jwd.core_final.context.impl.menu.MainInterface.SCANNER;

public class CreateEntityInterface implements UserInterface {
    static final CreateEntityInterface CREATE_ENTITY_INTERFACE = new CreateEntityInterface();
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateEntityInterface.class);

    @Override
    public void show() {
        LOGGER.info("Create interface");
        int input = -1;
        while (input != 0) {
            System.out.println("----------------------");
            System.out.println("Create:\n"
                    + "1.Create crew member\n"
                    + "2.Create mission\n"
                    + "0.Back ");
            System.out.println("----------------------");
            System.out.print("Please enter a number: ");
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
                System.out.println("Type 0 to exit");
                System.out.println("Write crew member name");
                while (true) {
                    try {
                        String name = scanner.nextLine();
                        System.out.println("Choose crew member role: ");
                        System.out.println("(1, 2, 3, 4): " + Arrays.toString(Role.values()));
                        int role = scanner.nextInt();
                        if (role == 0) break;
                        System.out.println("Choose crew member rank: ");
                        System.out.println("(1, 2, 3, 4): " + Arrays.toString(Rank.values()));
                        int rank = scanner.nextInt();
                        if (rank == 0) break;
                        CrewServiceImpl.CREW_SERVICE.createCrewMember(name, Role.resolveRoleById(role), Rank.resolveRankById(rank));
                        break;
                    } catch (UnknownEntityException | InputMismatchException ex) {
                        System.out.println("Wrong value");
                    } catch (IllegalArgumentException ex) {
                        System.out.println("Crew member already exists");
                    }
                }
                break;
            case 2:
                LOGGER.info("Creating mission");
                while (true) {
                    System.out.println("Mission name: ");
                    String missionName = scanner.next();
                    System.out.println("Mission distance: ");
                    Long distance = scanner.nextLong();
                    MissionServiceImpl.MISSION_SERVICE.createMission(missionName, distance);
                    break;
                }
                break;
            case 0:
                break;
            default:
                LOGGER.warn("Unexpected value");
                System.out.println("----------------------");
                System.out.println("Unexpected value: " + input);
        }
    }
}
