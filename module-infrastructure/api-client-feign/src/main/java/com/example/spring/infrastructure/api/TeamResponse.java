package com.example.spring.infrastructure.api;

import com.example.spring.domain.player.PlayerId;
import com.example.spring.domain.team.TeamId;
import com.example.spring.domain.team.TeamName;
import com.example.spring.domain.team.dto.TeamData;

import java.time.LocalDateTime;
import java.util.List;

public record TeamResponse(Long id, String name, LocalDateTime startsAt, List<Long> playerIds) {
    public TeamData toData() {
        return new TeamData(new TeamId(id), new TeamName(name), startsAt, playerIds.stream().map(PlayerId::new).toList());
    }
}
