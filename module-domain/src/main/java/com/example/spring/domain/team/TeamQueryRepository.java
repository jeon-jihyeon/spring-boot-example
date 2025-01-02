package com.example.spring.domain.team;

import com.example.spring.domain.team.dto.TeamData;
import com.example.spring.domain.team.model.TeamId;

import java.time.LocalDateTime;
import java.util.List;

public interface TeamQueryRepository {
    TeamData save(TeamData team);

    TeamData findById(TeamId id);

    void deleteById(TeamId id);

    List<TeamData> findTeamsAfter(LocalDateTime at);
}
