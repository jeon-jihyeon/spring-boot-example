package com.example.spring.boot.modules.player.domain.repository;

import com.example.spring.boot.modules.team.domain.model.TeamId;

import java.util.List;

public interface PlayerBulkCommandRepository {
    void updateAll(TeamId teamId, TeamId newId);

    void updateAll(TeamId teamId, List<Long> playerIds);
}
