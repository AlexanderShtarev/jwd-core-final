package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;

/**
 * Should be a builder for {@link com.epam.jwd.core_final.domain.CrewMember} fields
 */
public class CrewMemberCriteria extends Criteria<CrewMember> {
    final Role role;
    final Rank rank;
    final Boolean isReadyForNextMissions;

    public Role getRole() {
        return role;
    }

    public Rank getRank() {
        return rank;
    }

    public Boolean getReadyForNextMissions() {
        return isReadyForNextMissions;
    }

    CrewMemberCriteria(String name, Long id, Rank rank, Role role, Boolean isReadyForNextMissions) {
        super(name, id);
        this.role = role;
        this.rank = rank;
        this.isReadyForNextMissions = isReadyForNextMissions;
    }

    public static CrewMemberCriteria.Builder builder() {
        return new CrewMemberCriteria.Builder();
    }

    public static class Builder extends Criteria.Builder {

        Rank rank;
        Role role;
        Boolean isReadyForNextMissions;

        public void rank(Rank rank) {
            this.rank = rank;
        }

        public void role(Role role) {
            this.role = role;
        }

        public void isReadyForNextMissions(Boolean readyForNextMissions) {
            isReadyForNextMissions = readyForNextMissions;
        }

        public CrewMemberCriteria build() {
            return new CrewMemberCriteria(name, id, rank, role, isReadyForNextMissions);
        }
    }
}