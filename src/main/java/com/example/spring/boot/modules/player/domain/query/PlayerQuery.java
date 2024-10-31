package com.example.spring.boot.modules.player.domain.query;

import com.example.spring.boot.modules.player.domain.model.FullName;
import com.example.spring.boot.modules.player.domain.model.Grade;
import com.example.spring.boot.modules.player.domain.model.PlayerId;
import com.example.spring.boot.modules.team.domain.model.TeamId;

public record PlayerQuery(PlayerId id, Grade grade, FullName fullName, TeamId teamId) {
}
