package com.example.spring.domain.team;

import com.example.spring.domain.team.dto.TeamData;

import java.time.LocalDateTime;
import java.util.List;

public interface TeamQueryRepository {
    TeamData save(TeamData team);

    List<TeamData> findTeamsAfter(LocalDateTime dateTime);
}
