package com.example.spring.domain.team.model;

import com.example.spring.domain.player.model.PlayerId;
import com.example.spring.domain.team.dto.TeamCreateCommand;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        return new Team(TeamId.newId(), command.name(), LocalDateTime.now(), List.of());
    }

    public static Team of(TeamId id, TeamName name, LocalDateTime startsAt, List<PlayerId> playerIds) {
        return new Team(id, name, startsAt, playerIds);
    }

    public Team addPlayer(PlayerId playerId) {
        final List<PlayerId> newList = new ArrayList<>(playerIds);
        newList.add(playerId);
        return new Team(id, name, startsAt, newList);
    }

    public Team removePlayer(PlayerId playerId) {
        if (playerIds.isEmpty()) throw new EmptyPlayerIdsException();
        final List<PlayerId> newList = new ArrayList<>(playerIds);
        newList.remove(playerId);
        return new Team(id, name, startsAt, newList);
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
