package com.example.spring.domain.team.model;

import com.example.spring.domain.IdGenerator;

public record TeamId(Long value) {
    public static final TeamId NoTeam = new TeamId(0L);

    public static TeamId newId() {
        return new TeamId(IdGenerator.newId());
    }

    public Boolean isNotNoTeam() {
        return !value.equals(NoTeam.value);
    }
}
