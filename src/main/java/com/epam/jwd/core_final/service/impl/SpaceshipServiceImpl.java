package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.factory.impl.SpaceshipFactory;
import com.epam.jwd.core_final.service.SpaceshipService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.epam.jwd.core_final.context.impl.menu.MainInterface.SCANNER;

public class SpaceshipServiceImpl implements SpaceshipService {
    public static final SpaceshipServiceImpl SPACESHIP_SERVICE = new SpaceshipServiceImpl();

    private SpaceshipServiceImpl() {
    }

    @Override
    public List<Spaceship> findAllSpaceships() {
        if (NassaContext.NASSA_CONTEXT.retrieveBaseEntityList(Spaceship.class).isEmpty()) {
            System.out.println("You do not have any spaceships");
            return null;
        } else {
            return (List<Spaceship>) NassaContext.NASSA_CONTEXT.retrieveBaseEntityList(Spaceship.class);
        }
    }

    @Override
    public List<Spaceship> findAllSpaceshipsByCriteria(Criteria<? extends Spaceship> criteria) {
        SpaceshipCriteria spaceshipCriteria = (SpaceshipCriteria) criteria;
        return NassaContext.NASSA_CONTEXT.retrieveBaseEntityList(Spaceship.class).stream().filter(spaceship -> (
                (spaceship.getName().equals(spaceshipCriteria.getName()) || spaceshipCriteria.getName() == null)
                        && (spaceshipCriteria.getId() == null
                        || spaceship.getId().equals(spaceshipCriteria.getId()))
                        && (spaceshipCriteria.getFlightDistance() == null
                        || spaceship.getFlightDistance() == spaceshipCriteria.getFlightDistance())
                        && (spaceshipCriteria.getReadyForNextMissions() == null
                        || spaceship.getReadyForNextMissions() == spaceshipCriteria.getReadyForNextMissions())))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Spaceship> findSpaceshipByCriteria(Criteria<? extends Spaceship> criteria) {
        SpaceshipCriteria spaceshipCriteria = (SpaceshipCriteria) criteria;
        return NassaContext.NASSA_CONTEXT.retrieveBaseEntityList(Spaceship.class).stream().filter(spaceship -> (
                (spaceship.getName().equals(spaceshipCriteria.getName()) || spaceshipCriteria.getName() == null)
                        && (spaceshipCriteria.getId() == null
                        || spaceship.getId().equals(spaceshipCriteria.getId()))
                        && (spaceshipCriteria.getFlightDistance() == null
                        || spaceship.getFlightDistance() == spaceshipCriteria.getFlightDistance())
                        && (spaceshipCriteria.getReadyForNextMissions() == null
                        || spaceship.getReadyForNextMissions() == spaceshipCriteria.getReadyForNextMissions())))
                .findFirst();
    }

    @Override
    public Spaceship updateSpaceshipDetails(Spaceship spaceship) {
        System.out.println("ENTER NEW SHIP NAME: ");
        String spaceshipName = SCANNER.next();
        spaceship.setName(spaceshipName);
        System.out.println("ENTER FLIGHT DISTANCE OF THE SHIP: ");
        long spaceshipFlightDistance = SCANNER.nextLong();
        spaceship.setFlightDistance(spaceshipFlightDistance);
        System.out.println("Spaceship Updated");
        return spaceship;
    }

    @Override
    public void assignSpaceshipOnMission(FlightMission flightMission) throws RuntimeException {
        List<Spaceship> availableSpaceships = NassaContext.NASSA_CONTEXT.retrieveBaseEntityList(Spaceship.class).stream()
                .filter(spaceship -> spaceship.getFlightDistance() > flightMission.getDistance())
                .filter(Spaceship::getReadyForNextMissions)
                .collect(Collectors.toList());
        if (availableSpaceships.size() == 0)
            throw new UnknownEntityException("There is no available spaceships for this mission");
        System.out.println("Available Spaceships: \n" + Arrays.toString(availableSpaceships.toArray()) + "\nChoose by id: ");
        Optional<Spaceship> spaceship = Optional.empty();
        while (spaceship.isEmpty()) {
            Long id = SCANNER.nextLong();
            spaceship = findSpaceshipByCriteria(new SpaceshipCriteria.Builder() {{
                id(id);
            }}.build());
            if (spaceship.isPresent()) {
                if (!spaceship.get().getReadyForNextMissions()) {
                    System.out.println("This ship is not available!");
                    throw new IllegalArgumentException();
                }
                flightMission.setAssignedSpaceShip(spaceship.get());
                spaceship.get().setReadyForNextMissions(false);
            } else System.out.println("There is no spaceShip with this id");
        }
    }

    @Override
    public Spaceship createSpaceship(Object... args) throws RuntimeException {
        Optional<Spaceship> spaceship = findSpaceshipByCriteria(new SpaceshipCriteria.Builder() {{
            name((String) args[0]);
        }}.build());
        if (spaceship.isPresent()) throw new IllegalArgumentException("Spaceship already exists");
        return SpaceshipFactory.SPACESHIP_FACTORY.create(args);
    }
}