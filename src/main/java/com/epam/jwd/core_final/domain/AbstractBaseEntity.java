package com.epam.jwd.core_final.domain;

/**
 * Expected fields:
 * <p>
 * setId {@link Long} - entity setId
 * setName {@link String} - entity setName
 */
public abstract class AbstractBaseEntity implements BaseEntity {
    private final Long id;
    private String name;
    private static Long idCounter = 0L;

    AbstractBaseEntity(String name) {
        this.name = name;
        this.id = ++idCounter;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Long getId() {
        // todo
        return id;
    }

    @Override
    public String getName() {
        // todo
        return name;
    }
}