package com.example.spring.domain.player;

import com.example.spring.domain.player.dto.PlayerData;
import com.example.spring.domain.player.model.PlayerId;
import com.example.spring.domain.team.model.TeamId;

import java.util.List;

public interface PlayerQueryRepository {
    PlayerData save(PlayerData player);

    PlayerData findById(PlayerId id);

    List<PlayerData> findAllByTeamIds(List<TeamId> teamIds);

    void deleteById(PlayerId id);
}
