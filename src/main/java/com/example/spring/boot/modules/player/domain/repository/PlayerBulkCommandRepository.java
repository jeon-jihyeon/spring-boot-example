package com.example.spring.boot.modules.player.domain.repository;

import com.example.spring.boot.modules.team.domain.model.TeamId;

public interface PlayerBulkCommandRepository {
    void updateAllTeam(TeamId teamId, TeamId newId);
}
