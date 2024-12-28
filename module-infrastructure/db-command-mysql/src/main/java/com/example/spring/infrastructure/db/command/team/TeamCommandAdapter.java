package com.example.spring.infrastructure.db.command.team;

import com.example.spring.domain.team.TeamCommandRepository;
import com.example.spring.domain.team.dto.TeamCreateEvent;
import com.example.spring.domain.team.dto.TeamData;
import com.example.spring.domain.team.dto.TeamDeleteEvent;
import com.example.spring.domain.team.model.TeamId;
import com.example.spring.infrastructure.db.EntityNotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TeamCommandAdapter implements TeamCommandRepository {
    private final TeamJpaRepository repository;
    private final ApplicationEventPublisher publisher;

    public TeamCommandAdapter(TeamJpaRepository repository, ApplicationEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    @Override
    @Transactional(transactionManager = "commandTransactionManager")
    public TeamData save(TeamData team) {
        final TeamData saved = repository.save(TeamEntity.from(team)).toData();
        publisher.publishEvent(TeamCreateEvent.from(saved));
        return saved;
    }

    @Override
    public TeamData findById(TeamId id) {
        return repository.findById(id.value()).orElseThrow(EntityNotFoundException::new).toData();
    }

    @Override
    @Transactional(transactionManager = "commandTransactionManager")
    public void deleteById(TeamId id) {
        repository.deleteById(id.value());
        publisher.publishEvent(new TeamDeleteEvent(id));
    }
}
