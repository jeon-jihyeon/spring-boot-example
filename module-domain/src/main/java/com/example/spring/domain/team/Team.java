package com.example.spring.domain.team;

import com.example.spring.domain.player.PlayerId;
import com.example.spring.domain.team.dto.TeamCreateCommand;

import java.time.LocalDateTime;
import java.util.List;

public class Team {
    private final TeamId id;
    private final TeamName name;
    private final LocalDateTime startsAt;
    private final List<PlayerId> playerIds;

    private Team(TeamId id, TeamName name, LocalDateTime startsAt, List<PlayerId> playerIds) {
        this.id = id;
        this.name = name;
        this.startsAt = startsAt;
        this.playerIds = playerIds;
    }

    public static Team create(TeamCreateCommand command) {
        return new Team(
                TeamId.newId(),
                command.name(),
                LocalDateTime.now(),
                command.playerIds()
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

    public List<PlayerId> getPlayerIds() {
        return playerIds;
    }
}
