package com.example.spring.domain.command.player;

import com.example.spring.domain.command.player.dto.PlayerData;
import com.example.spring.domain.command.player.dto.PlayerLeaveCommand;
import com.example.spring.domain.event.DomainEventOutboxRepository;
import com.example.spring.domain.event.model.DomainEvent;
import com.example.spring.domain.event.model.DomainEventQueue;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerCommandLeaveService {
    private final PlayerCommandRepository repository;
    private final DomainEventOutboxRepository outboxRepository;

    public PlayerCommandLeaveService(PlayerCommandRepository repository, DomainEventOutboxRepository outboxRepository) {
        this.repository = repository;
        this.outboxRepository = outboxRepository;
    }

    @Transactional(transactionManager = "commandTransactionManager")
    public PlayerData invoke(PlayerLeaveCommand command) {
        final PlayerData data = PlayerData.from(repository.findById(command.playerId()).toModel().leaveTeam());
        outboxRepository.save(DomainEvent.updateType(DomainEventQueue.COMMAND_PLAYER.getName(), command.playerId().value()));
        return repository.save(data);
    }
}
