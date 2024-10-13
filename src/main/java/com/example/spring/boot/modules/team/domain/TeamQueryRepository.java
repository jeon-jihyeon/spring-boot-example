package com.example.spring.boot.modules.team.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeamQueryRepository {
    Page<Team> findTeams(TeamCondition condition, Pageable pageable);

    Team findTeam(Long id);
}
