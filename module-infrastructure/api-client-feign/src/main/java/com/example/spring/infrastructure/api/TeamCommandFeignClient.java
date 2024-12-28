package com.example.spring.infrastructure.api;

import com.example.spring.domain.team.TeamCommandApiClient;
import com.example.spring.domain.team.dto.TeamData;
import com.example.spring.domain.team.model.TeamId;
import org.springframework.stereotype.Component;

@Component
public class TeamCommandFeignClient implements TeamCommandApiClient {
    private final CommandFeignApi api;

    public TeamCommandFeignClient(CommandFeignApi api) {
        this.api = api;
    }

    @Override
    public TeamData findById(TeamId id) {
        return api.getTeam(id.value()).data().toData();
    }
}
