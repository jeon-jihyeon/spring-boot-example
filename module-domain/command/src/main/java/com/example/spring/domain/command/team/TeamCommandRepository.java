package com.example.spring.domain.command.team;

import com.example.spring.domain.command.team.dto.TeamData;
import com.example.spring.domain.command.team.model.TeamId;

public interface TeamCommandRepository {
    TeamData save(TeamData team);

    TeamData findById(TeamId id);

    void deleteById(TeamId id);
}
