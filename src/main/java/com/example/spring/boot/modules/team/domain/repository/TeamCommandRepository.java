package com.example.spring.boot.modules.team.domain.repository;

import com.example.spring.boot.modules.team.domain.model.Team;
import com.example.spring.boot.modules.team.domain.model.TeamId;

public interface TeamCommandRepository {
    Team findById(TeamId id);

    TeamId save(Team team);

    void deleteById(TeamId id);
}
