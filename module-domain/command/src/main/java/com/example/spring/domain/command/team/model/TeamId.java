package com.example.spring.domain.command.team.model;

import com.example.spring.domain.IdGenerator;

public record TeamId(Long value) {
    public static final TeamId NoTeam = new TeamId(0L);

    public static TeamId newId() {
        return new TeamId(IdGenerator.newId());
    }
}
