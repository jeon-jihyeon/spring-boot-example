package com.example.spring.domain.query.player.dto;

import com.example.spring.domain.command.team.model.TeamId;

import java.util.List;

public record PlayerQueryCondition(List<TeamId> teamIds) {
}
