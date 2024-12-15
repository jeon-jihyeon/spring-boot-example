package com.example.spring.domain.player.dto;

import com.example.spring.domain.player.Grade;
import com.example.spring.domain.player.Player;

public record PlayerData(Long id, Grade grade, String firstName, String lastName, Long teamId) {
    public static PlayerData from(Player player) {
        return new PlayerData(
                player.getId().value(),
                player.getGrade(),
                player.getFullName().firstName(),
                player.getFullName().lastName(),
                player.getTeamId().value()
        );
    }
}
