package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.factory.impl.FlightMissionFactory;
import com.epam.jwd.core_final.service.MissionService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.epam.jwd.core_final.context.impl.menu.MainInterface.SCANNER;

public class MissionServiceImpl implements MissionService {
    public static final MissionServiceImpl MISSION_SERVICE = new MissionServiceImpl();

    private MissionServiceImpl() {
    }

    @Override
    public List<FlightMission> findAllMissions() {
        return (List<FlightMission>) NassaContext.NASSA_CONTEXT.retrieveBaseEntityList(FlightMission.class);
    }

    @Override
    public List<FlightMission> findAllMissionsByCriteria(Criteria<? extends FlightMission> criteria) {
        FlightMissionCriteria flightMissionCriteria = (FlightMissionCriteria) criteria;
        return NassaContext.NASSA_CONTEXT.retrieveBaseEntityList(FlightMission.class).stream()
                .filter(flightMission -> ((flightMissionCriteria.getName() == null
                        || flightMission.getName().equals(flightMissionCriteria.getName()))
                        && (flightMissionCriteria.getId() == null
                        || flightMission.getId().equals(flightMissionCriteria.getId()))
                        && (flightMissionCriteria.getDistance() == null
                        || flightMission.getDistance() == flightMissionCriteria.getDistance())
                        && (flightMissionCriteria.getAssignedCrew() == null
                        || flightMission.getAssignedCrew() == flightMissionCriteria.getAssignedCrew())
                        && (flightMissionCriteria.getAssignedSpaceship() == null
                        || flightMission.getAssignedSpaceShip() == flightMissionCriteria.getAssignedSpaceship())
                        && (flightMissionCriteria.getMissionResult() == null
                        || flightMission.getMissionResult() == flightMissionCriteria.getMissionResult())
                        && (flightMissionCriteria.getStartDate() == null
                        || flightMission.getStartDate().equals(flightMissionCriteria.getStartDate()))
                        && (flightMissionCriteria.getEndDate() == null
                        || flightMission.getEndDate().equals(flightMissionCriteria.getEndDate()))))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<FlightMission> findMissionByCriteria(Criteria<? extends FlightMission> criteria) {
        FlightMissionCriteria flightMissionCriteria = (FlightMissionCriteria) criteria;
        return NassaContext.NASSA_CONTEXT.retrieveBaseEntityList(FlightMission.class).stream()
                .filter(flightMission -> ((flightMissionCriteria.getName() == null
                        || flightMission.getName().equals(flightMissionCriteria.getName()))
                        && (flightMissionCriteria.getId() == null
                        || flightMission.getId().equals(flightMissionCriteria.getId()))
                        && (flightMissionCriteria.getDistance() == null
                        || flightMission.getDistance() == flightMissionCriteria.getDistance())
                        && (flightMissionCriteria.getAssignedCrew() == null
                        || flightMission.getAssignedCrew() == flightMissionCriteria.getAssignedCrew())
                        && (flightMissionCriteria.getAssignedSpaceship() == null
                        || flightMission.getAssignedSpaceShip() == flightMissionCriteria.getAssignedSpaceship())
                        && (flightMissionCriteria.getMissionResult() == null
                        || flightMission.getMissionResult() == flightMissionCriteria.getMissionResult())
                        && (flightMissionCriteria.getStartDate() == null
                        || flightMission.getStartDate().equals(flightMissionCriteria.getStartDate()))
                        && (flightMissionCriteria.getEndDate() == null
                        || flightMission.getEndDate().equals(flightMissionCriteria.getEndDate()))))
                .findFirst();
    }

    @Override
    public FlightMission updateMissionDetails(FlightMission flightMission) {
        System.out.println("WRITE MISSION NAME: ");
        String name = SCANNER.next();
        flightMission.setName(name);
        System.out.println("WRITE MISSION DISTANCE: ");
        Long distance = SCANNER.nextLong();
        flightMission.setDistance(distance);
        System.out.println("Flight Mission Updated");
        return flightMission;
    }

    @Override
    public FlightMission createMission(Object... args) {
        return FlightMissionFactory.FLIGHT_MISSION_FACTORY.create(args);
    }
}