package com.example.spring.boot.modules.team.domain.repository;

import com.example.spring.boot.modules.team.domain.model.Team;
import com.example.spring.boot.modules.team.domain.model.TeamId;

public interface TeamCommandRepository {
    Long save(Team team);

    void delete(TeamId id);
}
