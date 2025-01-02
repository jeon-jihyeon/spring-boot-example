package com.example.spring.application.api.player.data;

import com.example.spring.domain.player.dto.PlayerQuery;
import com.example.spring.domain.player.model.Grade;

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
