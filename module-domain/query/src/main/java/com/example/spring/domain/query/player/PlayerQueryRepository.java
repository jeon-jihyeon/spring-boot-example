package com.example.spring.domain.query.player;

import com.example.spring.domain.command.player.dto.PlayerData;
import com.example.spring.domain.command.player.model.PlayerId;
import com.example.spring.domain.command.team.model.TeamId;

import java.util.List;

public interface PlayerQueryRepository {
    PlayerData create(PlayerData player);

    PlayerData update(PlayerData player);

    PlayerData findById(PlayerId id);

    List<PlayerData> findAllByTeamIds(List<TeamId> teamIds);

    void deleteById(PlayerId id);
}
