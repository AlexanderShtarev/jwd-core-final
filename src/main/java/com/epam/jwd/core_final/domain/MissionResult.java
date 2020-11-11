package com.epam.jwd.core_final.domain;

import com.epam.jwd.core_final.exception.UnknownEntityException;

public enum MissionResult implements BaseEntity {
    CANCELLED(1L),
    FAILED(2L),
    PLANNED(3L),
    IN_PROGRESS(4L),
    COMPLETED(5L);

    private final Long id;

    MissionResult(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name();
    }

    public static MissionResult resolveMissionResultById(int id) {
        for (MissionResult missionResult : MissionResult.values()) {
            if (missionResult.id.equals((long) id)) {
                return missionResult;
            }
        }
        throw new UnknownEntityException(String.valueOf(id));
    }
}
