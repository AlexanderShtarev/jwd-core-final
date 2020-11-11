package com.epam.jwd.core_final.context.impl.menu;

import com.epam.jwd.core_final.context.UserInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.InputMismatchException;

import static com.epam.jwd.core_final.context.impl.menu.MainInterface.SCANNER;

public class FindEntityInterface implements UserInterface {
    static final FindEntityInterface FIND_ENTITY_INTERFACE = new FindEntityInterface();
    private static final Logger LOGGER = LoggerFactory.getLogger(FindEntityInterface.class);

    @Override
    public void show() {
        LOGGER.info("Find entity interface");
        int input = -1;
        while (input != 0) {
            System.out.println("----------------------");
            System.out.println("Find:\n" +
                    "1.Find Crew Member\n" +
                    "2.Find Spaceship\n" +
                    "3.Find Mission\n" +
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
        switch (input) {
            case 1:
                FindCrewInterface.FIND_CREW_INTERFACE.show();
                break;
            case 2:
                FindShipInterface.FIND_SHIP_INTERFACE.show();
                break;
            case 3:
                FindMissionInterface.FIND_MISSION_INTERFACE.show();
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
