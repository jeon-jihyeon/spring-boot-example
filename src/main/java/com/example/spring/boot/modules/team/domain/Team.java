package com.example.spring.boot.modules.team.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Team {
    private final Long id;

    private final TeamName name;

    private final LocalDateTime startsAt;

    private final List<Long> playerIds;

    private Team(Long id, TeamName name, LocalDateTime startsAt, List<Long> playerIds) {
        this.id = id;
        this.name = name;
        this.startsAt = startsAt;
        this.playerIds = playerIds;
    }
}
