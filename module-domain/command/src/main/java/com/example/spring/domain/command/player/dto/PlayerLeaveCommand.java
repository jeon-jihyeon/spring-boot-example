package com.example.spring.domain.command.player.dto;

import com.example.spring.domain.command.player.model.PlayerId;

public record PlayerLeaveCommand(PlayerId playerId) {
}
