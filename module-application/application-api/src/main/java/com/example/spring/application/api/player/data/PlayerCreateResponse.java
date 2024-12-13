package com.example.spring.application.api.player.data;

import com.example.spring.domain.player.PlayerId;

public record PlayerCreateResponse(Long id) {
    public static PlayerCreateResponse from(PlayerId playerId) {
        return new PlayerCreateResponse(playerId.value());
    }
}
