package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.exception.InvalidStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;

// todo
public class NassaContext implements ApplicationContext {
    public static final NassaContext NASSA_CONTEXT = new NassaContext();
    private static final Logger LOGGER = LoggerFactory.getLogger(NassaContext.class);

    // no getters/setters for them
    private Collection<CrewMember> crewMembers = new ArrayList<>();
    private Collection<Spaceship> spaceships = new ArrayList<>();
    private Collection<FlightMission> flightMissions = new ArrayList<>();

    @Override
    public <T extends BaseEntity> Collection<T> retrieveBaseEntityList(Class<T> tClass) {
        Collection<T> entities = new ArrayList<>();
        if (tClass.equals(CrewMember.class)) {
            entities = (Collection<T>) crewMembers;
        }
        if (tClass.equals(Spaceship.class)) {
            entities = (Collection<T>) spaceships;
        }
        if (tClass.equals(FlightMission.class)) {
            entities = (Collection<T>) flightMissions;
        }
        return entities;
    }

    /**
     * You have to read input files, populate collections
     *
     * @throws InvalidStateException
     */
    @Override
    public void init() throws InvalidStateException {
        String crewFilePath = ApplicationProperties.APPLICATION_PROPERTIES.getInputRootDir()
                + ApplicationProperties.APPLICATION_PROPERTIES.getCrewFileName();
        String shipsFilePath = ApplicationProperties.APPLICATION_PROPERTIES.getInputRootDir()
                + ApplicationProperties.APPLICATION_PROPERTIES.getSpaceshipsFileName();
        try {
            ReadCrewFile.READ_CREW.read(crewFilePath);
        } catch (FileNotFoundException ex) {
            System.out.println("Crew file not found");
            LOGGER.error("CrewMember file not found");
            System.exit(0);
        }
        try {
            ReadShipFile.READ_SHIP_FILE.read(shipsFilePath);
        } catch (FileNotFoundException ex) {
            System.out.println("Ship file not found");
            LOGGER.error("Spaceship file not found");
            System.exit(0);
        }
    }
}