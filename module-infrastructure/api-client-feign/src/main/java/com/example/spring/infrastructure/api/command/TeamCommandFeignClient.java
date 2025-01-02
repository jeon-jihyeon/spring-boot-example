package com.example.spring.infrastructure.api.command;

import com.example.spring.domain.command.team.TeamCommandApiClient;
import com.example.spring.domain.command.team.dto.TeamData;
import com.example.spring.domain.command.team.model.TeamId;
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
