package com.example.spring.boot.modules.team.domain.repository;

import com.example.spring.boot.modules.team.domain.model.Team;
import com.example.spring.boot.modules.team.domain.model.TeamId;

public interface TeamCommandRepository {
    TeamId save(Team team);

    Team findById(TeamId id);

    void deleteById(TeamId id);
}
