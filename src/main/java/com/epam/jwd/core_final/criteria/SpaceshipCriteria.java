package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.Spaceship;

/**
 * Should be a builder for {@link Spaceship} fields
 */
public class SpaceshipCriteria extends Criteria<Spaceship> {
    final Long flightDistance;
    final Boolean isReadyForNextMissions;

    public Long getFlightDistance() {
        return flightDistance;
    }

    public Boolean getReadyForNextMissions() {
        return isReadyForNextMissions;
    }

    SpaceshipCriteria(String name, Long id, Long flightDistance, Boolean isReadyForTheNextMissions) {
        super(name, id);
        this.flightDistance = flightDistance;
        this.isReadyForNextMissions = isReadyForTheNextMissions;
    }

    public static SpaceshipCriteria.Builder builder() {
        return new SpaceshipCriteria.Builder();
    }

    public static class Builder extends Criteria.Builder {
        Long flightDistance;
        Boolean isReadyForNextMissions;

        public void flightDistance(Long flightDistance) {
            this.flightDistance = flightDistance;
        }

        public void isReadyForNextMissions(Boolean isReadyForNextMissions) {
            this.isReadyForNextMissions = isReadyForNextMissions;
        }

        public SpaceshipCriteria build() {
            return new SpaceshipCriteria(name, id, flightDistance, isReadyForNextMissions);
        }
    }
}