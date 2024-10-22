package com.example.spring.boot.modules.team.domain.model;

import com.example.spring.boot.core.IdGenerator;

public record TeamId(Long value) {
    public static final TeamId NoTeam = new TeamId(0L);

    public static TeamId newId() {
        return new TeamId(IdGenerator.newId());
    }
}
