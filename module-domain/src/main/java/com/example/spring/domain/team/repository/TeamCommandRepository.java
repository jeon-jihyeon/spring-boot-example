package com.example.spring.domain.team.repository;

import com.example.spring.domain.team.Team;
import com.example.spring.domain.team.TeamId;

public interface TeamCommandRepository {
    TeamId save(Team team);

    Team findById(TeamId id);

    void deleteById(TeamId id);
}
