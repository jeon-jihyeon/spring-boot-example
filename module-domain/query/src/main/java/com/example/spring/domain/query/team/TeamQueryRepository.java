package com.example.spring.domain.query.team;

import com.example.spring.domain.command.team.dto.TeamData;
import com.example.spring.domain.command.team.model.TeamId;

import java.time.LocalDateTime;
import java.util.List;

public interface TeamQueryRepository {
    TeamData create(TeamData team);

    TeamData update(TeamData team);

    TeamData findById(TeamId id);

    void deleteById(TeamId id);

    List<TeamData> findTeamsAfter(LocalDateTime at);
}
