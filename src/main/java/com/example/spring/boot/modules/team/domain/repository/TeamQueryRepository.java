package com.example.spring.boot.modules.team.domain.repository;

import com.example.spring.boot.modules.team.domain.model.TeamId;
import com.example.spring.boot.modules.team.domain.repository.condition.TeamCondition;
import com.example.spring.boot.modules.team.domain.repository.query.TeamListQuery;
import com.example.spring.boot.modules.team.domain.repository.query.TeamQuery;

import java.util.List;

public interface TeamQueryRepository {
    List<TeamListQuery> findTeams(TeamCondition condition);

    TeamQuery findTeam(TeamId id);
}
