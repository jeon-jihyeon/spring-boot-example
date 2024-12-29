package com.example.spring.domain.team;

import com.example.spring.domain.team.dto.*;
import com.example.spring.domain.team.model.Team;
import com.example.spring.domain.team.model.TeamId;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeamCommandService {
    private final TeamCommandRepository repository;
    private final ApplicationEventPublisher publisher;

    public TeamCommandService(TeamCommandRepository repository, ApplicationEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    public TeamData read(TeamId id) {
        return repository.findById(id);
    }

    @Transactional(transactionManager = "commandTransactionManager")
    public TeamData create(TeamCreateCommand command) {
        final TeamData data = repository.save(TeamData.from(Team.create(command)));
        publisher.publishEvent(TeamCreateEvent.from(data));
        return data;
    }

    @Transactional(transactionManager = "commandTransactionManager")
    public void delete(TeamDeleteCommand command) {
        repository.deleteById(command.id());
        publisher.publishEvent(TeamDeleteEvent.from(command));
    }
}
