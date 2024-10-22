package com.example.spring.boot.modules.team.domain.repository;

import com.example.spring.boot.modules.team.domain.TeamCondition;
import com.example.spring.boot.modules.team.domain.model.Team;
import com.example.spring.boot.modules.team.domain.model.TeamId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeamQueryRepository {
    Page<Team> findTeams(TeamCondition condition, Pageable pageable);

    Team findTeam(TeamId id);
}
