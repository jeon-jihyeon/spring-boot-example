package com.example.spring.domain.command.team;

import com.example.spring.domain.command.team.dto.TeamCreateCommand;
import com.example.spring.domain.command.team.dto.TeamData;
import com.example.spring.domain.command.team.dto.TeamDeleteCommand;
import com.example.spring.domain.command.team.model.Team;
import com.example.spring.domain.command.team.model.TeamId;
import com.example.spring.domain.event.DomainEventOutboxRepository;
import com.example.spring.domain.event.model.DomainEvent;
import com.example.spring.domain.event.model.DomainEventQueue;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeamCommandService {
    private final DomainEventOutboxRepository outboxRepository;
    private final TeamCommandRepository repository;

    public TeamCommandService(DomainEventOutboxRepository outboxRepository, TeamCommandRepository repository) {
        this.outboxRepository = outboxRepository;
        this.repository = repository;
    }

    public TeamData read(TeamId id) {
        return repository.findById(id);
    }

    @Transactional(transactionManager = "commandTransactionManager")
    public TeamData create(TeamCreateCommand command) {
        final TeamData data = repository.save(TeamData.from(Team.create(command)));
        outboxRepository.save(DomainEvent.createType(DomainEventQueue.COMMAND_TEAM.getName(), data.id().value()));
        return data;
    }

    @Transactional(transactionManager = "commandTransactionManager")
    public void delete(TeamDeleteCommand command) {
        repository.deleteById(command.teamId());
        outboxRepository.save(DomainEvent.deleteType(DomainEventQueue.COMMAND_TEAM.getName(), command.teamId().value()));
        outboxRepository.save(DomainEvent.deleteType(DomainEventQueue.DELETE_TEAM.getName(), command.teamId().value()));
    }
}
