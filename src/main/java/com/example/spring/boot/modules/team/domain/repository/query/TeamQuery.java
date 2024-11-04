package com.example.spring.boot.modules.team.domain.repository.query;

import com.example.spring.boot.modules.player.domain.model.PlayerId;
import com.example.spring.boot.modules.team.domain.model.TeamId;
import com.example.spring.boot.modules.team.domain.model.TeamName;

import java.time.LocalDateTime;
import java.util.List;

public record TeamQuery(TeamId id, TeamName name, LocalDateTime startsAt, List<PlayerId> playerIds) {
}
