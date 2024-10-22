package com.example.spring.boot.modules.team.domain.command;

import com.example.spring.boot.modules.player.domain.model.PlayerId;
import com.example.spring.boot.modules.team.domain.model.TeamId;

public record TeamAddPlayer(TeamId teamId, PlayerId playerId) {
}
