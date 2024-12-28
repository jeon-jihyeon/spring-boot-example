package com.example.spring.infrastructure.api;

import com.example.spring.domain.player.model.PlayerId;
import com.example.spring.domain.team.dto.TeamData;
import com.example.spring.domain.team.model.TeamId;
import com.example.spring.domain.team.model.TeamName;

import java.time.LocalDateTime;
import java.util.List;

public record TeamApiResponse(Long id, String name, LocalDateTime startsAt, List<Long> playerIds) {
    public TeamData toData() {
        return new TeamData(new TeamId(id), new TeamName(name), startsAt, playerIds.stream().map(PlayerId::new).toList());
    }
}
