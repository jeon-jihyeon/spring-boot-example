package com.example.spring.domain.team;

import com.example.spring.domain.team.dto.TeamData;
import com.example.spring.domain.team.model.TeamId;

public interface TeamCommandApiClient {
    TeamData findById(TeamId id);
}
