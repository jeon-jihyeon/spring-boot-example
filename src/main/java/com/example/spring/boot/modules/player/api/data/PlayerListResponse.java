package com.example.spring.boot.modules.player.api.data;

import com.example.spring.boot.modules.player.domain.model.Grade;
import com.example.spring.boot.modules.player.domain.repository.query.PlayerListQuery;

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
