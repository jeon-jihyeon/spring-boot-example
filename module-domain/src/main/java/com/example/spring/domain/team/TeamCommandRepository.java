package com.example.spring.domain.team;

import com.example.spring.domain.team.dto.TeamData;
import com.example.spring.domain.team.model.TeamId;

public interface TeamCommandRepository {
    TeamData save(TeamData team);

    TeamData findById(TeamId id);

    void deleteById(TeamId id);
}
