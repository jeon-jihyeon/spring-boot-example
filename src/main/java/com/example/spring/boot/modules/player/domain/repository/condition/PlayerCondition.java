package com.example.spring.boot.modules.player.domain.repository.condition;

import com.example.spring.boot.modules.player.domain.model.FullName;
import com.example.spring.boot.modules.player.domain.model.Grade;
import com.example.spring.boot.modules.team.domain.model.TeamId;

public record PlayerCondition(Grade grade, FullName fullName, TeamId teamId) {
}
