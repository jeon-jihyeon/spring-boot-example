package com.example.spring.domain.command.player.dto;

import com.example.spring.domain.command.player.model.PlayerId;
import com.example.spring.domain.command.team.model.TeamId;

public record PlayerJoinCommand(PlayerId playerId, TeamId teamId) {
}
