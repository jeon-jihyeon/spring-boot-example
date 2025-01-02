package com.example.spring.application.api.player.data;

import com.example.spring.domain.player.dto.PlayerData;
import com.example.spring.domain.player.model.Grade;

public record PlayerCommandResponse(Long id, Grade grade, String firstName, String lastName, Long teamId) {
    public static PlayerCommandResponse from(PlayerData data) {
        return new PlayerCommandResponse(
                data.id().value(),
                data.grade(),
                data.fullName().firstName(),
                data.fullName().lastName(),
                data.teamId().value()
        );
    }
}
