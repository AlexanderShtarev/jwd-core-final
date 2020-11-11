package com.epam.jwd.core_final.domain;

/**
 * Expected fields:
 * <p>
 * role {@link Role} - member role
 * rank {@link Rank} - member rank
 * isReadyForNextMissions {@link Boolean} - true by default. Set to false, after first failed mission
 */
public class CrewMember extends AbstractBaseEntity {
    // todo
    private Role role;
    private Rank rank;
    private Boolean isReadyForNextMissions;

    public CrewMember(String name, Role role, Rank rank) {
        super(name);
        this.rank = rank;
        this.role = role;
        isReadyForNextMissions = true;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public void setReadyForNextMissions(boolean readyForNextMissions) {
        isReadyForNextMissions = readyForNextMissions;
    }

    public Role getRole() {
        return role;
    }

    public Rank getRank() {
        return rank;
    }

    public Boolean isReadyForNextMissions() {
        return isReadyForNextMissions;
    }

    @Override
    public String toString() {
        return "\nCrewMember{" +
                "id: " + getId() +
                ", name: " + getName() +
                ", role: " + role +
                ", rank: " + rank +
                ", isReadyForNextMissions: " + isReadyForNextMissions +
                '}';
    }
}