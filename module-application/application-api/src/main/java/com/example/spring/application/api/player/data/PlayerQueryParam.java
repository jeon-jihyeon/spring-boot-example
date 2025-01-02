package com.example.spring.application.api.player.data;

import com.example.spring.application.common.InvalidValueException;
import com.example.spring.domain.player.dto.PlayerQueryCondition;
import com.example.spring.domain.team.model.TeamId;

import java.util.List;

public record PlayerQueryParam(List<Long> teamIds) {
    public PlayerQueryParam {
        if (teamIds.isEmpty()) throw new InvalidValueException();
    }

    public PlayerQueryCondition toCondition() {
        return new PlayerQueryCondition(teamIds.stream().map(TeamId::new).toList());
    }
}
