package com.example.spring.infrastructure.api;

import com.example.spring.domain.player.FullName;
import com.example.spring.domain.player.Grade;
import com.example.spring.domain.player.PlayerId;
import com.example.spring.domain.player.dto.PlayerData;
import com.example.spring.domain.team.TeamId;

public record PlayerApiResponse(Long id, Grade grade, String firstName, String lastName, Long teamId) {
    public PlayerData toData() {
        return new PlayerData(new PlayerId(id), grade, new FullName(firstName, lastName), new TeamId(teamId));
    }
}
