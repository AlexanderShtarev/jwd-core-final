package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.factory.EntityFactory;

public class FlightMissionFactory implements EntityFactory {
    public static final FlightMissionFactory FLIGHT_MISSION_FACTORY = new FlightMissionFactory();

    private FlightMissionFactory() {
    }

    @Override
    public FlightMission create(Object... args) {
        if (args.length != 2) throw new IllegalArgumentException();
        FlightMission flightMission = new FlightMission((String) args[0], (Long) args[1]);
        NassaContext.NASSA_CONTEXT.retrieveBaseEntityList(FlightMission.class).add(flightMission);
        flightMission.setMissionResult(MissionResult.PLANNED);
        return flightMission;
    }
}
