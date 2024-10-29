package com.example.spring.boot.modules.team.domain.model;

import com.example.spring.boot.core.IdGenerator;
import com.example.spring.boot.core.exception.InvalidValueException;

public record TeamId(Long value) {
    public static final TeamId NoTeam = new TeamId(0L);

    public TeamId {
        if (value == null || value < 0) throw new InvalidValueException();
    }

    public static TeamId newId() {
        return new TeamId(IdGenerator.newId());
    }
}
