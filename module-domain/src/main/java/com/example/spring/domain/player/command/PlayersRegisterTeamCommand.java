package com.example.spring.domain.player.command;

import com.example.spring.domain.team.TeamId;

import java.util.List;

public record PlayersRegisterTeamCommand(TeamId teamId, List<Long> playerIds) {
}
