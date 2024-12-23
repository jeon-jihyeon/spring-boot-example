package com.example.spring.domain.team;

import com.example.spring.domain.team.dto.TeamData;

public interface TeamQueryRepository {
    TeamData save(TeamData team);
}
