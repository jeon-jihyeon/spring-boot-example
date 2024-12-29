package com.example.spring.domain.player.dto;

import com.example.spring.domain.team.model.TeamId;

public record PlayersLeaveCommand(TeamId teamId) {
}
