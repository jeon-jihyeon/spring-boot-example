package com.example.spring.domain.player.query;

import com.example.spring.domain.player.Grade;
import com.example.spring.domain.player.Player;

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
