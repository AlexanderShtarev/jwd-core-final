package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.domain.Spaceship;

import java.util.ArrayList;
import java.util.List;

/**
 * Should be a builder for {@link com.epam.jwd.core_final.domain.FlightMission} fields
 */
public class FlightMissionCriteria extends Criteria<FlightMission> {

    private java.time.LocalDateTime startDate;
    private java.time.LocalDateTime endDate;
    private Long distance;
    private Spaceship assignedSpaceShip = null;
    private java.util.List<CrewMember> assignedCrew = new ArrayList<>();
    private MissionResult missionResult;

    public FlightMissionCriteria(String name,
                                 Long id,
                                 java.time.LocalDateTime startDate,
                                 java.time.LocalDateTime endDate,
                                 Long distance,
                                 Spaceship assignedSpaceship,
                                 List<CrewMember> assignedCrew,
                                 MissionResult missionResult) {
        super(name, id);
        this.startDate = startDate;
        this.endDate = endDate;
        this.distance = distance;
        this.assignedSpaceShip = assignedSpaceship;
        this.assignedCrew = assignedCrew;
        this.missionResult = missionResult;
    }

    public List<CrewMember> getAssignedCrew() {
        return assignedCrew;
    }

    public java.time.LocalDateTime getEndDate() {
        return endDate;
    }

    public java.time.LocalDateTime getStartDate() {
        return startDate;
    }

    public Long getDistance() {
        return distance;
    }

    public Spaceship getAssignedSpaceship() {
        return assignedSpaceShip;
    }

    public MissionResult getMissionResult() {
        return missionResult;
    }

    public static FlightMissionCriteria.Builder builder() {
        return new FlightMissionCriteria.Builder();
    }

    public static class Builder extends Criteria.Builder {
        java.time.LocalDateTime startDate;
        java.time.LocalDateTime endDate;
        Long distance;
        Spaceship assignedSpaceship;
        List<CrewMember> assignedCrew;
        MissionResult missionResult;

        public void startDate(java.time.LocalDateTime startDate) {
            this.startDate = startDate;
        }

        public void endDate(java.time.LocalDateTime endDate) {
            this.endDate = endDate;
        }

        public void distance(Long distance) {
            this.distance = distance;
        }

        public void assignedSpaceship(Spaceship assignedSpaceship) {
            this.assignedSpaceship = assignedSpaceship;
        }

        public void assignedCrew(List<CrewMember> assignedCrew) {
            this.assignedCrew = assignedCrew;
        }

        public void missionResult(MissionResult missionResult) {
            this.missionResult = missionResult;
        }

        public FlightMissionCriteria build() {
            return new FlightMissionCriteria(name, id, startDate, endDate, distance, assignedSpaceship, assignedCrew, missionResult);
        }
    }
}