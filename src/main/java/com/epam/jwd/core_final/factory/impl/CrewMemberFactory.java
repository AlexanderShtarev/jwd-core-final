package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.factory.EntityFactory;

// do the same for other entities
public class CrewMemberFactory implements EntityFactory<CrewMember> {
    public static final CrewMemberFactory CREW_MEMBER_FACTORY = new CrewMemberFactory();

    private CrewMemberFactory() {
    }

    @Override
    public CrewMember create(Object... args) {
        if (args.length != 3) throw new IllegalArgumentException();
        CrewMember crewMember = new CrewMember((String) args[0], (Role) args[1], (Rank) args[2]);
        NassaContext.NASSA_CONTEXT.retrieveBaseEntityList(CrewMember.class).add(crewMember);
        return crewMember;
    }
}