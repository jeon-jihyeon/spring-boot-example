package com.example.spring.domain.player.dto;

import com.example.spring.domain.player.model.PlayerId;
import com.example.spring.domain.team.model.TeamId;

public record PlayerJoinCommand(PlayerId playerId, TeamId teamId) {
}
