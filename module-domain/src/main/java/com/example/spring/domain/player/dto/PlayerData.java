package com.example.spring.domain.player.dto;

import com.example.spring.domain.player.FullName;
import com.example.spring.domain.player.Grade;
import com.example.spring.domain.player.Player;
import com.example.spring.domain.player.PlayerId;
import com.example.spring.domain.team.TeamId;

public record PlayerData(PlayerId id, Grade grade, FullName fullName, TeamId teamId) {
    public static PlayerData from(Player player) {
        return new PlayerData(player.getId(), player.getGrade(), player.getFullName(), player.getTeamId());
    }
}
