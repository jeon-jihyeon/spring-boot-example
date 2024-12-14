package com.example.spring.domain.team.repository;

import com.example.spring.domain.team.query.TeamListQuery;
import com.example.spring.domain.team.repository.condition.TeamCondition;

import java.util.List;

public interface TeamQueryRepository {
    List<TeamListQuery> findTeams(TeamCondition condition);
}
