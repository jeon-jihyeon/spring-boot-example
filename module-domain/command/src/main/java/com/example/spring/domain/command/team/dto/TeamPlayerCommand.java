package com.example.spring.domain.command.team.dto;

import com.example.spring.domain.command.player.model.PlayerId;

public record TeamPlayerCommand(PlayerId playerId) {
}
