package com.example.spring.domain.team.dto;

import com.example.spring.domain.player.model.PlayerId;

public record TeamPlayerCommand(PlayerId playerId) {
}
