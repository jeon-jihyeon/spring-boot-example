package com.example.spring.boot.modules.player.domain.repository.command;

import com.example.spring.boot.modules.team.domain.model.TeamId;

import java.util.List;

public record PlayersRegisterTeamCommand(TeamId teamId, List<Long> playerIds) {
}
