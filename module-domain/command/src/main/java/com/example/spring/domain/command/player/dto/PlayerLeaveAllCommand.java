package com.example.spring.domain.command.player.dto;

import com.example.spring.domain.command.team.model.TeamId;

public record PlayerLeaveAllCommand(TeamId teamId) {
}
