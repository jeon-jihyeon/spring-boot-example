package com.example.spring.infrastructure.api;

import com.example.spring.domain.team.TeamApiClient;
import com.example.spring.domain.team.TeamId;
import com.example.spring.domain.team.dto.TeamData;
import org.springframework.stereotype.Component;

@Component
public class TeamFeignApiClient implements TeamApiClient {
    private final CommandFeignApi api;

    public TeamFeignApiClient(CommandFeignApi api) {
        this.api = api;
    }

    @Override
    public TeamData findById(TeamId id) {
        return api.getTeam(id.value()).data().toData();
    }
}
