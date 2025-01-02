package com.example.spring.domain.command.player.dto;

import com.example.spring.domain.command.player.model.FullName;
import com.example.spring.domain.command.player.model.Grade;
import com.example.spring.domain.command.player.model.Player;
import com.example.spring.domain.command.player.model.PlayerId;
import com.example.spring.domain.command.team.model.TeamId;

public record PlayerData(PlayerId id, Grade grade, FullName fullName, TeamId teamId) {
    public static PlayerData from(Player player) {
        return new PlayerData(player.getId(), player.getGrade(), player.getFullName(), player.getTeamId());
    }

    public static PlayerData of(Long id, Grade grade, String firstName, String lastName, Long teamId) {
        return new PlayerData(new PlayerId(id), grade, new FullName(firstName, lastName), new TeamId(teamId));
    }

    public Player toModel() {
        return Player.of(id, grade, fullName, teamId);
    }
}
