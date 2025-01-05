package com.example.spring.infrastructure.db.query.team;

import com.example.spring.domain.command.team.dto.TeamData;
import com.example.spring.domain.command.team.model.TeamId;
import com.example.spring.domain.query.team.TeamQueryRepository;
import com.example.spring.infrastructure.db.query.DocumentNotFoundException;
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
    public TeamData create(TeamData team) {
        return repository.save(TeamDocument.create(team)).toData();
    }

    @Override
    public TeamData update(TeamData team) {
        return repository.save(repository.findById(team.id().value()).orElseThrow(DocumentNotFoundException::new).update(team)).toData();
    }

    @Override
    public TeamData findById(TeamId id) {
        return repository.findById(id.value()).orElseThrow(DocumentNotFoundException::new).toData();
    }

    @Override
    public void deleteById(TeamId id) {
        repository.deleteById(id.value());
    }

    @Override
    public List<TeamData> findTeamsAfter(LocalDateTime at) {
        return repository.findAllByStartsAtAfter(at).stream().map(TeamDocument::toData).toList();
    }
}
