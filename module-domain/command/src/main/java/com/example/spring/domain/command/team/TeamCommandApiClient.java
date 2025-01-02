package com.example.spring.domain.command.team;

import com.example.spring.domain.command.team.dto.TeamData;
import com.example.spring.domain.command.team.model.TeamId;

public interface TeamCommandApiClient {
    TeamData findById(TeamId id);
}
