package com.example.spring.infrastructure.db.query.team;

import com.example.spring.domain.team.TeamQueryRepository;
import com.example.spring.domain.team.dto.TeamData;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class TeamQueryAdapter implements TeamQueryRepository {
    private final TeamMongoRepository repository;

    public TeamQueryAdapter(TeamMongoRepository repository) {
        this.repository = repository;
    }

    @Override
    public TeamData save(TeamData team) {
        return repository.save(TeamDocument.from(team)).toData();
    }

    @Override
    public List<TeamData> findTeamsAfter(LocalDateTime dateTime) {
        // TODO:
        return null;
    }
}
