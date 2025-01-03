package com.example.spring.application.api.player.data;

import com.example.spring.domain.command.player.model.Grade;
import com.example.spring.domain.query.player.dto.PlayerQuery;

public record PlayerQueryResponse(Long id, Grade grade, String firstName, String lastName, Long teamId) {
    public static PlayerQueryResponse from(PlayerQuery query) {
        return new PlayerQueryResponse(
                query.id(),
                query.grade(),
                query.firstName(),
                query.lastName(),
                query.teamId()
        );
    }
}