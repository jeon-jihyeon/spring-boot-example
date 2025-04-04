package com.example.spring.api.response;


import com.example.spring.domain.command.dto.PlayerData;
import com.example.spring.domain.command.model.Grade;

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
                data.point().value(),
                data.fullName().firstName(),
                data.fullName().lastName(),
                data.teamId().value()
        );
    }
}
