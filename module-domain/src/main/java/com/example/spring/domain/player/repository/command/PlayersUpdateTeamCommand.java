package com.example.spring.domain.player.repository.command;

import com.example.spring.domain.team.TeamId;

public record PlayersUpdateTeamCommand(TeamId teamId, TeamId newId) {
}
