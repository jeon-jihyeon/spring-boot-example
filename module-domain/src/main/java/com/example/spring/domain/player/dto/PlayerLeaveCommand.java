package com.example.spring.domain.player.dto;

import com.example.spring.domain.player.model.PlayerId;

public record PlayerLeaveCommand(PlayerId playerId) {
}
