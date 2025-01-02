package com.example.spring.application.api.team.data;

import com.example.spring.domain.player.model.PlayerId;
import com.example.spring.domain.team.dto.TeamData;

import java.time.LocalDateTime;
import java.util.List;

public record TeamCommandResponse(Long id, String name, LocalDateTime startsAt, List<Long> playerIds) {
    public static TeamCommandResponse from(TeamData data) {
        return new TeamCommandResponse(
                data.id().value(),
                data.name().value(),
                data.startsAt(),
                data.playerIds().stream().map(PlayerId::value).toList()
        );
    }
}
