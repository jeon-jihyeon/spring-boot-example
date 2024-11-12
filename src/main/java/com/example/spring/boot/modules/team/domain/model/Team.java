package com.example.spring.boot.modules.team.domain.model;

import com.example.spring.boot.modules.team.domain.repository.command.TeamCreateCommand;

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

    public static Team of(Long id, String name, LocalDateTime startsAt) {
        return new Team(
                new TeamId(id),
                new TeamName(name),
                startsAt
        );
    }

    public static Team create(TeamCreateCommand command) {
        return new Team(
                TeamId.newId(),
                command.name(),
                LocalDateTime.now()
        );
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
