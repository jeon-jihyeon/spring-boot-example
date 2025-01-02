package com.example.spring.domain.command.player.dto;

import com.example.spring.domain.command.player.model.PlayerId;

public record PlayerTeamEvent(Long playerId) {
    public static PlayerTeamEvent from(PlayerId playerId) {
        return new PlayerTeamEvent(playerId.value());
    }
}
