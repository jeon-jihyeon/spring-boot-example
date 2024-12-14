package com.example.spring.domain.team.command;

import com.example.spring.domain.player.PlayerId;
import com.example.spring.domain.team.TeamId;

public record TeamAddPlayer(TeamId teamId, PlayerId playerId) {
}
