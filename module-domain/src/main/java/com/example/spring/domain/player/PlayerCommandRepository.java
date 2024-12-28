package com.example.spring.domain.player;

import com.example.spring.domain.player.dto.PlayerData;
import com.example.spring.domain.player.model.PlayerId;
import com.example.spring.domain.team.model.TeamId;

import java.util.List;

public interface PlayerCommandRepository {
    PlayerData save(PlayerData player);

    PlayerData findById(PlayerId id);

    void updateAll(TeamId teamId, List<Long> playerIds);

    void updateAll(TeamId teamId);

    void deleteById(PlayerId id);
}
