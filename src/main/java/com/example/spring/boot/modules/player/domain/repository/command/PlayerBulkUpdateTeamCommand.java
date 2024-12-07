package com.example.spring.boot.modules.player.domain.repository.command;

import com.example.spring.boot.modules.team.domain.model.TeamId;

public record PlayerBulkUpdateTeamCommand(TeamId teamId, TeamId newId) {
}
