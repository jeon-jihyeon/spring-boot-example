package com.example.spring.domain.player.dto;

import com.example.spring.domain.player.model.PlayerId;

public record PlayerTeamEvent(Long playerId) {
    public static PlayerTeamEvent from(PlayerId playerId) {
        return new PlayerTeamEvent(playerId.value());
    }
}
