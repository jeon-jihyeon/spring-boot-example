package com.example.spring.boot.modules.player.api.response;

import com.example.spring.boot.modules.player.domain.query.PlayerListQuery;

public record PlayerListResponse(Long id, String grade, String firstName, String lastName, Boolean hasTeam) {
    public static PlayerListResponse from(PlayerListQuery query) {
        return new PlayerListResponse(
                query.id().value(),
                query.grade().name(),
                query.fullName().firstName(),
                query.fullName().lastName(),
                query.hasTeam()
        );
    }
}
