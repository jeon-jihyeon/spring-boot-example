package com.example.spring.infrastructure.db.query.team;

import com.example.spring.domain.team.TeamQueryRepository;
import com.example.spring.domain.team.dto.TeamData;
import com.example.spring.domain.team.model.TeamId;
import com.example.spring.infrastructure.db.query.DocumentNotFoundException;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class TeamQueryAdapter implements TeamQueryRepository {
    private final TeamMongoRepository repository;

    public TeamQueryAdapter(TeamMongoRepository repository) {
        this.repository = repository;
    }

    @Override
    public TeamData save(TeamData team) {
        final Optional<TeamDocument> existing = repository.findById(team.id().value());
        return repository.save(existing.isPresent() ? existing.get().update(team) : TeamDocument.from(team)).toData();
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
