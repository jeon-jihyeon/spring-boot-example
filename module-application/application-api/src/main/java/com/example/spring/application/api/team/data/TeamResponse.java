package com.example.spring.application.api.team.data;

import com.example.spring.domain.player.model.PlayerId;
import com.example.spring.domain.team.dto.TeamData;

import java.time.LocalDateTime;
import java.util.List;

public record TeamResponse(Long id, String name, LocalDateTime startsAt, List<Long> playerIds) {
    public static TeamResponse from(TeamData data) {
        return new TeamResponse(
                data.id().value(),
                data.name().value(),
                data.startsAt(),
                data.playerIds().stream().map(PlayerId::value).toList()
        );
    }
}
