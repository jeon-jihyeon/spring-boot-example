package com.example.spring.application.api.player.data;

import com.example.spring.domain.player.Grade;
import com.example.spring.domain.player.query.PlayerListQuery;

public record PlayerListResponse(Long id, Grade grade, String firstName, String lastName, Long teamId) {
    public static PlayerListResponse from(PlayerListQuery query) {
        return new PlayerListResponse(
                query.id(),
                query.grade(),
                query.firstName(),
                query.lastName(),
                query.teamId()
        );
    }
}
