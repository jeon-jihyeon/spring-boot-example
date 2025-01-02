package com.example.spring.domain.player.dto;

import com.example.spring.domain.team.model.TeamId;

import java.util.List;

public record PlayerQueryCondition(List<TeamId> teamIds) {
}
