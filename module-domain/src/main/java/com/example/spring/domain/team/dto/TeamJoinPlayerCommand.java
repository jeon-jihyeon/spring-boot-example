package com.example.spring.domain.team.dto;

import com.example.spring.domain.player.model.PlayerId;
import com.example.spring.domain.team.model.TeamId;

public record TeamJoinPlayerCommand(PlayerId playerId, TeamId teamId) {
}
