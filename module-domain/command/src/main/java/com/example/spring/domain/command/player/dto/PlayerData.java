package com.example.spring.domain.command.player.dto;

import com.example.spring.domain.command.player.model.FullName;
import com.example.spring.domain.command.player.model.Grade;
import com.example.spring.domain.command.player.model.Player;
import com.example.spring.domain.command.player.model.PlayerId;
import com.example.spring.domain.command.team.model.TeamId;

public record PlayerData(PlayerId id, Grade grade, Integer point, FullName fullName, TeamId teamId) {
    public static PlayerData from(Player p) {
        return new PlayerData(p.getId(), p.getGrade(), p.getPoint(), p.getFullName(), p.getTeamId());
    }

    public static PlayerData of(Long id, Grade grade, Integer point, String firstName, String lastName, Long teamId) {
        return new PlayerData(new PlayerId(id), grade, point, new FullName(firstName, lastName), new TeamId(teamId));
    }

    public Player toModel() {
        return Player.of(id, grade, point, fullName, teamId);
    }
}
