package com.example.spring.domain.team;

import com.example.spring.domain.team.dto.TeamData;

public interface TeamApiClient {
    TeamData findById(TeamId id);
}
