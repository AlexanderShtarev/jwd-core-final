package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.factory.EntityFactory;

import java.util.Map;

public class SpaceshipFactory implements EntityFactory {
    public static final SpaceshipFactory SPACESHIP_FACTORY = new SpaceshipFactory();

    private SpaceshipFactory() {
    }

    @Override
    public Spaceship create(Object... args) {
        if (args.length != 3) throw new IllegalArgumentException();
        Spaceship spaceship = new Spaceship((String) args[0], (Map<Role, Short>) args[1], (Long) args[2]);
        NassaContext.NASSA_CONTEXT.retrieveBaseEntityList(Spaceship.class).add(spaceship);
        return spaceship;
    }
}