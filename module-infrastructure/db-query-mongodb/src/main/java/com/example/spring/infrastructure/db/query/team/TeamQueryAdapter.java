package com.example.spring.infrastructure.db.query.team;

import com.example.spring.domain.team.TeamQueryRepository;
import com.example.spring.domain.team.dto.TeamData;
import org.springframework.stereotype.Repository;

@Repository
public class TeamQueryAdapter implements TeamQueryRepository {
    @Override
    public TeamData save(TeamData team) {
        return null;
    }
}
