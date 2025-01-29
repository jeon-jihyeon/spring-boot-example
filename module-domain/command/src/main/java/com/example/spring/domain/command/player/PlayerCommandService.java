package com.example.spring.domain.command.player;

import com.example.spring.domain.DistributedLock;
import com.example.spring.domain.command.player.dto.PlayerCreateCommand;
import com.example.spring.domain.command.player.dto.PlayerData;
import com.example.spring.domain.command.player.dto.PlayerPointCommand;
import com.example.spring.domain.command.player.model.Player;
import com.example.spring.domain.command.player.model.PlayerId;
import com.example.spring.domain.event.DomainEventOutboxRepository;
import com.example.spring.domain.event.model.DomainEvent;
import com.example.spring.domain.event.model.DomainEventQueue;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerCommandService {
    private final PlayerCommandRepository repository;
    private final DomainEventOutboxRepository outboxRepository;

    public PlayerCommandService(PlayerCommandRepository repository, DomainEventOutboxRepository outboxRepository) {
        this.repository = repository;
        this.outboxRepository = outboxRepository;
    }

    public PlayerData read(PlayerId id) {
        return repository.findById(id);
    }

    @Transactional(transactionManager = "commandTransactionManager")
    public PlayerData create(PlayerCreateCommand command) {
        final PlayerData data = repository.save(PlayerData.from(Player.create(command)));
        outboxRepository.save(DomainEvent.createType(DomainEventQueue.COMMAND_PLAYER.getName(), data.id().value()));
        return data;
    }

    @Transactional(transactionManager = "commandTransactionManager")
    public void delete(PlayerId id) {
        repository.deleteById(id);
        outboxRepository.save(DomainEvent.deleteType(DomainEventQueue.COMMAND_PLAYER.getName(), id.value()));
    }

    @DistributedLock(key = "#command.getKey()")
    public void addPoint(PlayerPointCommand command) {
        final Player player = repository.findById(command.playerId()).toModel();
        repository.save(PlayerData.from(player.addPoint(command)));
        outboxRepository.save(DomainEvent.updateType(DomainEventQueue.COMMAND_PLAYER.getName(), command.playerId().value()));
    }
}
