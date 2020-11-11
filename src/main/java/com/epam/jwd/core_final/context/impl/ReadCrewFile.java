package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ReadBaseEntityFile;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Scanner;

public class ReadCrewFile implements ReadBaseEntityFile {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReadCrewFile.class);
    static final ReadCrewFile READ_CREW = new ReadCrewFile();

    @Override
    public void read(String filePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filePath));
        if (!scanner.hasNextLine()) throw new IllegalArgumentException("Non readable file");
        String line = scanner.nextLine();
        scanner.useDelimiter(";");
        while (scanner.hasNext()) {
            line = scanner.next();
            String[] lineSplit = line.split(",", 3);
            try {
                CrewServiceImpl.CREW_SERVICE.createCrewMember(lineSplit[1],
                        Role.resolveRoleById(Integer.parseInt(lineSplit[0])),
                        Rank.resolveRankById(Integer.parseInt(lineSplit[2])));
                LOGGER.info("Crew Member Created");
            } catch (UnknownEntityException ex) {
                LOGGER.error("Unknown Entity");
            } catch (IllegalArgumentException ex) {
                LOGGER.error("Duplicate CrewMember");
            } catch (RuntimeException ex) {
                LOGGER.error("Wrong arguments");
            }
        }
        scanner.close();
    }
}
