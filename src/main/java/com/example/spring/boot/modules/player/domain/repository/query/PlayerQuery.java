package com.example.spring.boot.modules.player.domain.repository.query;

import com.example.spring.boot.modules.player.domain.model.Grade;
import com.example.spring.boot.modules.player.domain.model.Player;

public record PlayerQuery(Long id, Grade grade, String firstName, String lastName, Long teamId) {
    public static PlayerQuery from(Player domain) {
        return new PlayerQuery(
                domain.getId().value(),
                domain.getGrade(),
                domain.getFullName().firstName(),
                domain.getFullName().lastName(),
                domain.getTeamId().value()
        );
    }
}
