package com.example.spring.domain.command.team;

import com.example.spring.domain.command.team.dto.TeamCreateCommand;
import com.example.spring.domain.command.team.dto.TeamData;
import com.example.spring.domain.command.team.dto.TeamDeleteCommand;
import com.example.spring.domain.command.team.dto.TeamDeleteEvent;
import com.example.spring.domain.command.team.model.Team;
import com.example.spring.domain.command.team.model.TeamId;
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

    public TeamData create(TeamCreateCommand command) {
        return repository.save(TeamData.from(Team.create(command)));
    }

    @Transactional(transactionManager = "commandTransactionManager")
    public void delete(TeamDeleteCommand command) {
        repository.deleteById(command.teamId());
        publisher.publishEvent(TeamDeleteEvent.from(command));
    }
}
