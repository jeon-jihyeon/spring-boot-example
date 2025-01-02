package com.example.spring.domain.command.team.model;

import com.example.spring.domain.command.team.dto.TeamCreateCommand;

import java.time.LocalDateTime;

public class Team {
    private final TeamId id;
    private final TeamName name;
    private final LocalDateTime startsAt;

    private Team(TeamId id, TeamName name, LocalDateTime startsAt) {
        this.id = id;
        this.name = name;
        this.startsAt = startsAt;
    }

    public static Team create(TeamCreateCommand command) {
        return new Team(TeamId.newId(), command.name(), LocalDateTime.now());
    }

    public static Team of(TeamId id, TeamName name, LocalDateTime startsAt) {
        return new Team(id, name, startsAt);
    }

    public TeamId getId() {
        return id;
    }

    public TeamName getName() {
        return name;
    }

    public LocalDateTime getStartsAt() {
        return startsAt;
    }
}
