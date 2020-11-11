package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.BaseEntity;

/**
 * Should be a builder for {@link BaseEntity} fields
 */

public abstract class Criteria<T extends BaseEntity> {

    private final String name;
    private final Long id;

    Criteria(String name, Long id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public static abstract class Builder {
        public Builder() {
            this.configure();
        }

        void configure() {
        }

        String name;
        Long id;

        public void name(String value) {
            this.name = value;
        }

        public void id(Long value) {
            this.id = value;
        }

        public abstract Criteria build();
    }
}