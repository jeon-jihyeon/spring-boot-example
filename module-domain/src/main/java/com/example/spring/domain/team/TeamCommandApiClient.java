package com.example.spring.domain.team;

import com.example.spring.domain.team.dto.TeamData;

public interface TeamCommandApiClient {
    TeamData findById(TeamId id);
}
