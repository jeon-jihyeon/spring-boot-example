package com.example.spring.application.api.player.response;

import com.example.spring.domain.command.player.dto.PlayerData;
import com.example.spring.domain.command.player.model.Grade;

public record PlayerCommandResponse(
        Long id,
        Grade grade,
        Integer point,
        String firstName,
        String lastName,
        Long teamId
) {
    public static PlayerCommandResponse from(PlayerData data) {
        return new PlayerCommandResponse(
                data.id().value(),
                data.grade(),
                data.point(),
                data.fullName().firstName(),
                data.fullName().lastName(),
                data.teamId().value()
        );
    }
}
