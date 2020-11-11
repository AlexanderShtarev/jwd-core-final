package com.epam.jwd.core_final.context.impl.menu;

import com.epam.jwd.core_final.context.UserInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainInterface implements UserInterface {
    public final static Scanner SCANNER = new Scanner(System.in);
    public final static MainInterface MAIN_INTERFACE = new MainInterface();
    private static final Logger LOGGER = LoggerFactory.getLogger(MainInterface.class);

    public void show() {
        LOGGER.info("Main interface");
        int input = -1;
        while (true) {
            System.out.println("----------------------");
            System.out.println("Main Menu: \n" +
                    "1.New Game\n" +
                    "2.Create\n" +
                    "3.Update\n" +
                    "4.Find\n" +
                    "5.Write\n" +
                    "6.Exit");
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
                NewGameInterface.NEW_GAME_INTERFACE.show();
                break;
            case 2:
                CreateEntityInterface.CREATE_ENTITY_INTERFACE.show();
                break;
            case 3:
                UpdateEntityInterface.UPDATE_ENTITY_INTERFACE.show();
                break;
            case 4:
                FindEntityInterface.FIND_ENTITY_INTERFACE.show();
                break;
            case 5:
                WriteEntityInterface.WRITE_ENTITY_INTERFACE.show();
                break;
            case 6:
                LOGGER.info("Exit");
                System.out.println("Exit...");
                System.exit(0);
                break;
            default:
                System.out.println("----------------------");
                LOGGER.warn("Unexpected value");
                System.out.println("Unexpected value: " + input);
        }
    }
}