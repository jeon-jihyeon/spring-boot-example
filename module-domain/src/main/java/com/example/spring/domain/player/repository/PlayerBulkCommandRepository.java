package com.example.spring.domain.player.repository;

import com.example.spring.domain.team.TeamId;

import java.util.List;

public interface PlayerBulkCommandRepository {
    void updateAll(TeamId teamId, TeamId newId);

    void updateAll(TeamId teamId, List<Long> playerIds);
}
