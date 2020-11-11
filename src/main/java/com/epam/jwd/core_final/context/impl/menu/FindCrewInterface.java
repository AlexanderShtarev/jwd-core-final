package com.epam.jwd.core_final.context.impl.menu;

import com.epam.jwd.core_final.context.UserInterface;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static com.epam.jwd.core_final.context.impl.menu.MainInterface.SCANNER;

public class FindCrewInterface implements UserInterface {
    static final FindCrewInterface FIND_CREW_INTERFACE = new FindCrewInterface();
    private static final Logger LOGGER = LoggerFactory.getLogger(FindCrewInterface.class);

    @Override
    public void show() {
        LOGGER.info("Find crew interface");
        int input = -1;
        while (input != 0) {
            System.out.println("----------------------");
            System.out.println("Find Crew Member:\n" +
                    "1.Find Crew Member by Name\n" +
                    "2.Find Crew Member by Id\n" +
                    "3.Find Crew Member by Role and Rank\n" +
                    "4.Find Crew Member by Mission Readiness\n" +
                    "5.Find All Crew Members\n" +
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
        Optional<CrewMember> crewMember;
        Scanner scanner = new Scanner(System.in);
        switch (input) {
            case 1:
                LOGGER.info("Finding Crew Member by Name");
                System.out.println("The name of the member: ");
                String name = scanner.nextLine();
                crewMember = CrewServiceImpl.CREW_SERVICE.findCrewMemberByCriteria(new CrewMemberCriteria.Builder() {{
                    name(name);
                }}.build());
                crewMember.ifPresent(crewMember1 -> System.out.println(crewMember.get().toString()));
                if (crewMember.isEmpty()) System.out.println("Crew Member not found");
                scanner.close();
                break;
            case 2:
                LOGGER.info("Finding Crew Member by Id");
                System.out.println("The id of the member: ");
                Long id = scanner.nextLong();
                crewMember = CrewServiceImpl.CREW_SERVICE.findCrewMemberByCriteria(new CrewMemberCriteria.Builder() {{
                    id(id);
                }}.build());
                crewMember.ifPresent(crewMember1 -> System.out.println(crewMember.get().toString()));
                if (crewMember.isEmpty()) System.out.println("Crew Member not found");
                scanner.close();
                break;
            case 3:
                LOGGER.info("Finding Crew Member by Role and Rank");
                System.out.println("Role number: " + Arrays.toString(Role.values()));
                int role = scanner.nextInt();
                System.out.println("Rank number: " + Arrays.toString(Rank.values()));
                int rank = scanner.nextInt();
                try {
                    crewMember = CrewServiceImpl.CREW_SERVICE.findCrewMemberByCriteria(new CrewMemberCriteria.Builder() {{
                        rank(Rank.resolveRankById(rank));
                        role(Role.resolveRoleById(role));
                    }}.build());
                    crewMember.ifPresent(crewMember1 -> System.out.println(crewMember.get().toString()));
                    if (crewMember.isEmpty()) System.out.println("Crew Member not found");
                } catch (UnknownEntityException ex) {
                    System.out.println("Unknown entity id");
                    LOGGER.error("Unknown entity id");
                }
                scanner.close();
                break;
            case 4:
                LOGGER.info("Finding Crew Member by Mission Readiness");
                System.out.println("Readiness for the next mission(true,false): ");
                Boolean trueFalse = scanner.nextBoolean();
                crewMember = CrewServiceImpl.CREW_SERVICE.findCrewMemberByCriteria(new CrewMemberCriteria.Builder() {{
                    isReadyForNextMissions(trueFalse);
                }}.build());
                crewMember.ifPresent(crewMember1 -> System.out.println(crewMember.get().toString()));
                if (crewMember.isEmpty()) System.out.println("Crew Member not found");
                scanner.close();
                break;
            case 5:
                LOGGER.info("Finding All Crew Members");
                List<CrewMember> crewMembers = CrewServiceImpl.CREW_SERVICE.findAllCrewMembers();
                if (crewMembers.isEmpty()) System.out.println("You do not have any crew members");
                else System.out.println(Arrays.toString(crewMembers.toArray()));
                scanner.close();
                break;
            case 0:
                break;
            default:
                LOGGER.warn("Unexpected value: ");
                System.out.println("----------------------");
                System.out.println("Unexpected value: " + input);
                break;
        }
    }
}
