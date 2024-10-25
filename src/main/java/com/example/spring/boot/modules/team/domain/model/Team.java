package com.example.spring.boot.modules.team.domain.model;

import com.example.spring.boot.modules.player.domain.model.PlayerId;
import com.example.spring.boot.modules.team.domain.command.TeamChangeName;
import com.example.spring.boot.modules.team.domain.command.TeamCreate;

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

    public static Team create(TeamCreate command) {
        return new Team(
                TeamId.newId(),
                command.name(),
                LocalDateTime.now(),
                List.of()
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

    public Team update(TeamChangeName command) {
        return new Team(id, command.name(), startsAt, playerIds);
    }
}
