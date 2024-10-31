package com.example.spring.boot.modules.team.domain.repository;

import com.example.spring.boot.modules.team.domain.condition.TeamCondition;
import com.example.spring.boot.modules.team.domain.model.TeamId;
import com.example.spring.boot.modules.team.domain.query.TeamListQuery;
import com.example.spring.boot.modules.team.domain.query.TeamQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeamQueryRepository {
    Page<TeamListQuery> findTeams(TeamCondition condition, Pageable pageable);

    TeamQuery findTeam(TeamId id);
}
