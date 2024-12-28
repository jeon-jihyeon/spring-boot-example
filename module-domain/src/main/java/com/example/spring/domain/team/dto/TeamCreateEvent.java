package com.example.spring.domain.team.dto;

import com.example.spring.domain.player.model.PlayerId;
import com.example.spring.domain.team.model.TeamId;

import java.util.List;

public record TeamCreateEvent(TeamId teamId, List<PlayerId> playerIds) {
    public static TeamCreateEvent from(TeamData data) {
        return new TeamCreateEvent(data.id(), data.playerIds());
    }
}
