package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.service.CrewService;

import java.util.*;
import java.util.stream.Collectors;

import static com.epam.jwd.core_final.context.impl.menu.MainInterface.SCANNER;

public class CrewServiceImpl implements CrewService {
    public static final CrewServiceImpl CREW_SERVICE = new CrewServiceImpl();

    private CrewServiceImpl() {
    }

    @Override
    public List<CrewMember> findAllCrewMembers() {
        return (List<CrewMember>) NassaContext.NASSA_CONTEXT.retrieveBaseEntityList(CrewMember.class);
    }

    @Override
    public List<CrewMember> findAllCrewMembersByCriteria(Criteria<? extends CrewMember> criteria) {
        List<CrewMember> crewMembers = findAllCrewMembers();
        CrewMemberCriteria crewMemberCriteria = (CrewMemberCriteria) criteria;
        return crewMembers.stream().filter(crewMember -> (
                (crewMember.getName().equals(crewMemberCriteria.getName()) || crewMemberCriteria.getName() == null)
                        && (crewMember.getRank() == crewMemberCriteria.getRank() || crewMemberCriteria.getRank() == null)
                        && (crewMember.isReadyForNextMissions() == crewMemberCriteria.getReadyForNextMissions() || crewMemberCriteria.getReadyForNextMissions() == null)
                        && (crewMember.getRole() == crewMemberCriteria.getRole() || crewMemberCriteria.getRole() == null)
        )).collect(Collectors.toList());
    }

    @Override
    public Optional<CrewMember> findCrewMemberByCriteria(Criteria<? extends CrewMember> criteria) {
        List<CrewMember> crewMembers = findAllCrewMembers();
        CrewMemberCriteria crewMemberCriteria = (CrewMemberCriteria) criteria;
        return crewMembers.stream().filter(crewMember -> (
                (crewMemberCriteria.getName() == null || crewMember.getName().equals(crewMemberCriteria.getName()))
                        && (crewMemberCriteria.getRank() == null || crewMember.getRank() == crewMemberCriteria.getRank())
                        && (crewMemberCriteria.getReadyForNextMissions() == null || crewMember.isReadyForNextMissions() == crewMemberCriteria.getReadyForNextMissions())
                        && (crewMemberCriteria.getRole() == null || crewMember.getRole() == crewMemberCriteria.getRole())
        )).findFirst();
    }

    @Override
    public CrewMember updateCrewMemberDetails(CrewMember crewMember) {
        while (true) {
            try {
                System.out.println("Type 0 to exit");
                System.out.println("Choose crew member role: ");
                System.out.println("(1, 2, 3, 4): " + Arrays.toString(Role.values()));
                int role = SCANNER.nextInt();
                if (role == 0) break;
                crewMember.setRole(Role.resolveRoleById(role));
                System.out.println("Role updated");
                System.out.println("Choose crew member rank: ");
                System.out.println("(1, 2, 3, 4): " + Arrays.toString(Rank.values()));
                int rank = SCANNER.nextInt();
                if (rank == 0) break;
                crewMember.setRank(Rank.resolveRankById(rank));
                System.out.println("Rank updated");
                break;
            } catch (UnknownEntityException | InputMismatchException ex) {
                System.out.println("Wrong value");
            }
        }
        return crewMember;
    }

    @Override
    public void assignCrewMembersOnMission(FlightMission flightMission) throws RuntimeException {
        Spaceship spaceship = flightMission.getAssignedSpaceShip();
        Map<Role, Short> requiredCrew = spaceship.getCrew();
        Map<Role, Short> assignedCrew = new HashMap<>();
        Role[] roles = Role.values();
        for (Role role : roles) {
            if (requiredCrew.get(role) != null) { //if role needed
                for (int i = 0; i < requiredCrew.get(role); i++) {
                    Optional<CrewMember> crewMember = CrewServiceImpl.CREW_SERVICE.findCrewMemberByCriteria(
                            new CrewMemberCriteria.Builder() {{
                                role(role);
                                isReadyForNextMissions(true);
                            }}.build());
                    System.out.println(crewMember.toString());
                    if (crewMember.isPresent()) {
                        crewMember.get().setReadyForNextMissions(false);
                        System.out.println(crewMember.toString());
                        assignedCrew.put(crewMember.get().getRole(), (short) (i + 1)); //starts from one
                    }
                }
            }
        }
        System.out.println(assignedCrew.toString());
        System.out.println(requiredCrew.toString());
        if (!requiredCrew.equals(assignedCrew)) throw new IllegalArgumentException("Not enough crew members");
        spaceship.setAssignedCrew(assignedCrew);
    }

    @Override
    public CrewMember createCrewMember(Object... args) throws RuntimeException {
        Optional<CrewMember> crewMember = findCrewMemberByCriteria(new CrewMemberCriteria.Builder() {{
            name((String) args[0]);
        }}.build());
        if (crewMember.isPresent()) throw new IllegalArgumentException("Crew member already exists");
        return CrewMemberFactory.CREW_MEMBER_FACTORY.create(args);
    }
}