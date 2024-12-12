package com.example.spring.domain.team.repository;

import com.example.spring.domain.team.TeamId;
import com.example.spring.domain.team.repository.condition.TeamCondition;
import com.example.spring.domain.team.repository.query.TeamListQuery;
import com.example.spring.domain.team.repository.query.TeamQuery;

import java.util.List;

public interface TeamQueryRepository {
    List<TeamListQuery> findTeams(TeamCondition condition);

    TeamQuery findTeam(TeamId id);
}
