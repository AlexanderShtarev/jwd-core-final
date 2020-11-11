package com.epam.jwd.core_final.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Expected fields:
 * <p>
 * missions setName {@link String}
 * start date {@link java.time.LocalDate}
 * end date {@link java.time.LocalDate}
 * distance {@link Long} - missions distance
 * assignedSpaceShift {@link Spaceship} - not defined by default
 * assignedCrew {@link java.util.List<CrewMember>} - list of missions members based on ship capacity - not defined by default
 * missionResult {@link MissionResult}
 */
public class FlightMission extends AbstractBaseEntity {
    // todo
    private java.time.LocalDateTime startDate;
    private java.time.LocalDateTime endDate;
    private Long distance;
    private Spaceship assignedSpaceShip = null;
    private java.util.List<CrewMember> assignedCrew = new ArrayList<>();
    private MissionResult missionResult;

    public FlightMission(String name, Long distance) {
        super(name);
        this.distance = distance;
    }

    public MissionResult getMissionResult() {
        return missionResult;
    }

    public void addCrewMember(CrewMember crewMember) {
        assignedCrew.add(crewMember);
    }

    public String getEndDate() {
        return endDate.format(DateTimeFormatter
                .ofPattern((ApplicationProperties.APPLICATION_PROPERTIES.getDateTimeFormat())));
    }

    public Long getDistance() {
        return distance;
    }

    public Spaceship getAssignedSpaceShip() {
        return assignedSpaceShip;
    }

    public List<CrewMember> getAssignedCrew() {
        return assignedCrew;
    }

    public String getStartDate() {
        return startDate.format(DateTimeFormatter
                .ofPattern((ApplicationProperties.APPLICATION_PROPERTIES.getDateTimeFormat())));
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setAssignedSpaceShip(Spaceship assignedSpaceShip) {
        this.assignedSpaceShip = assignedSpaceShip;
    }

    public void setAssignedCrew(List<CrewMember> assignedCrew) {
        this.assignedCrew = assignedCrew;
    }

    public void setMissionResult(MissionResult missionResult) {
        this.missionResult = missionResult;
    }

    @Override
    public String toString() {
        return "\nFlightMission{" +
                "id: " + getId() +
                ", name: " + getName() +
                ", startDate: " + startDate +
                ", endDate: " + endDate +
                ", distance: " + distance +
                ", assignedSpaceShip: " + assignedSpaceShip +
                ", assignedCrew: " + assignedCrew +
                ", missionResult: " + missionResult +
                '}';
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }
}
