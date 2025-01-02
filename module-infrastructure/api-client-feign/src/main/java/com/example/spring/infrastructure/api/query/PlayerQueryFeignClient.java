package com.example.spring.infrastructure.api.query;

import com.example.spring.domain.command.player.dto.PlayerData;
import com.example.spring.domain.command.team.model.TeamId;
import com.example.spring.domain.query.player.PlayerQueryApiClient;
import com.example.spring.infrastructure.api.PlayerApiResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlayerQueryFeignClient implements PlayerQueryApiClient {
    private final QueryFeignApi api;

    public PlayerQueryFeignClient(QueryFeignApi api) {
        this.api = api;
    }

    @Override
    public List<PlayerData> findAllByTeamIds(List<TeamId> teamIds) {
        return api.findPlayers(teamIds.stream().map(TeamId::value).toList()).data().stream().map(PlayerApiResponse::toData).toList();
    }
}
