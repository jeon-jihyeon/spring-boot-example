package com.example.spring.domain.query.player;

import com.example.spring.domain.command.player.dto.PlayerData;
import com.example.spring.domain.command.team.model.TeamId;

import java.util.List;

public interface PlayerQueryApiClient {
    List<PlayerData> findAllByTeamIds(List<TeamId> teamIds);
}
