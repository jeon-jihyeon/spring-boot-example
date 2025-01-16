package com.example.spring.application.api.player.request;

import com.example.spring.domain.command.team.model.TeamId;
import com.example.spring.domain.query.player.dto.PlayerQueryCondition;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record PlayerQueryParam(@NotEmpty List<Long> teamIds) {
    public PlayerQueryCondition toCondition() {
        return new PlayerQueryCondition(teamIds.stream().map(TeamId::new).toList());
    }
}
