package com.example.spring.infrastructure.api;

import com.example.spring.domain.command.player.dto.PlayerData;
import com.example.spring.domain.command.player.model.FullName;
import com.example.spring.domain.command.player.model.Grade;
import com.example.spring.domain.command.player.model.PlayerId;
import com.example.spring.domain.command.player.model.PlayerPoint;
import com.example.spring.domain.command.team.model.TeamId;

public record PlayerApiResponse(Long id, Grade grade, Integer point, String firstName, String lastName, Long teamId) {
    public PlayerData toData() {
        return new PlayerData(new PlayerId(id), grade, new PlayerPoint(point), new FullName(firstName, lastName), new TeamId(teamId));
    }
}