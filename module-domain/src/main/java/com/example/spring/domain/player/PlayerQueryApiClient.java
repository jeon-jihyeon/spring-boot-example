package com.example.spring.domain.player;

import com.example.spring.domain.player.dto.PlayerData;
import com.example.spring.domain.team.model.TeamId;

import java.util.List;

public interface PlayerQueryApiClient {
    List<PlayerData> findAllByTeamIds(List<TeamId> teamIds);
}
