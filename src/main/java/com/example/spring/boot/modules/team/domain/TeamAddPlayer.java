package com.example.spring.boot.modules.team.domain;

import com.example.spring.boot.modules.player.domain.PlayerId;

public record TeamAddPlayer(TeamId teamId, PlayerId playerId) {
}
