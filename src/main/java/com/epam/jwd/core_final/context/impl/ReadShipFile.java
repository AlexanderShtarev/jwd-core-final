package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ReadBaseEntityFile;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.service.impl.SpaceshipServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ReadShipFile implements ReadBaseEntityFile {
    static final ReadShipFile READ_SHIP_FILE = new ReadShipFile();
    private static final Logger LOGGER = LoggerFactory.getLogger(ReadShipFile.class);

    public void read(String filePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filePath));
        if (!scanner.hasNextLine()) throw new IllegalArgumentException("Non readable file");
        String fileLine;
        scanner.nextLine();
        scanner.nextLine();
        scanner.nextLine();
        while (scanner.hasNext()) {
            Map<Role, Short> roleShortMap = new HashMap<>();
            scanner.useDelimiter("\\r?\\n");
            fileLine = scanner.next();
            String[] fileLineSplit = fileLine.split(";", 3);
            scanner.useDelimiter(":");
            String fileLineSplitRoleAmount = fileLineSplit[2].substring(1, fileLineSplit[2].length() - 1);
            String[] eachRoleAmountSplit = fileLineSplitRoleAmount.split(",", 4);

            int i = 0;
            while (i != 4) {
                String[] roleAmountSplit = eachRoleAmountSplit[i].split(":", 2);
                try {
                    roleShortMap.put(Role.resolveRoleById(Integer.parseInt(roleAmountSplit[0])),
                            Short.parseShort(roleAmountSplit[1]));
                } catch (UnknownEntityException ex) {
                    LOGGER.error("Unknown Entity");
                }
                i++;
            }
            try {
                SpaceshipServiceImpl.SPACESHIP_SERVICE.createSpaceship(fileLineSplit[0], roleShortMap,
                        Long.parseLong(fileLineSplit[1]));
                LOGGER.info("Spaceship created");
            } catch (IllegalArgumentException ex) {
                LOGGER.error("Duplicated Spaceship");
            } catch (RuntimeException ex) {
                LOGGER.error("Wrong Arguments");
            }
        }
        scanner.close();
    }
}