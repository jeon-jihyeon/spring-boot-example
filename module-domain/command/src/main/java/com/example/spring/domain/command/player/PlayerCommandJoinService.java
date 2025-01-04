package com.example.spring.domain.command.player;

import com.example.spring.domain.command.player.dto.PlayerData;
import com.example.spring.domain.command.player.dto.PlayerJoinCommand;
import com.example.spring.domain.command.team.TeamCommandApiClient;
import com.example.spring.domain.command.team.model.TeamId;
import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventOutboxRepository;
import com.example.spring.domain.event.DomainEventQueue;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerCommandJoinService {
    private final PlayerCommandRepository repository;
    private final TeamCommandApiClient teamClient;
    private final DomainEventOutboxRepository outboxRepository;

    public PlayerCommandJoinService(PlayerCommandRepository repository, TeamCommandApiClient teamClient, DomainEventOutboxRepository outboxRepository) {
        this.repository = repository;
        this.teamClient = teamClient;
        this.outboxRepository = outboxRepository;
    }

    @Transactional(transactionManager = "commandTransactionManager")
    public PlayerData invoke(PlayerJoinCommand command) {
        final PlayerData data = repository.findById(command.playerId());
        final TeamId teamId = teamClient.findById(command.teamId()).id();
        outboxRepository.save(DomainEvent.updateType(DomainEventQueue.COMMAND_PLAYER.getName(), data.id().value()));
        return repository.save(PlayerData.from(data.toModel().joinTeam(teamId)));
    }
}
