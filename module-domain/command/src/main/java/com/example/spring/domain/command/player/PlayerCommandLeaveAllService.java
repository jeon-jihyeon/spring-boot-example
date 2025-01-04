package com.example.spring.domain.command.player;

import com.example.spring.domain.command.player.dto.PlayerLeaveAllCommand;
import com.example.spring.domain.command.player.model.PlayerId;
import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventOutboxRepository;
import com.example.spring.domain.event.DomainEventQueue;
import com.example.spring.domain.event.MessageBatchProducer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlayerCommandLeaveAllService {
    private final MessageBatchProducer batchProducer;
    private final PlayerCommandRepository repository;
    private final DomainEventOutboxRepository outboxRepository;

    public PlayerCommandLeaveAllService(
            MessageBatchProducer batchProducer,
            PlayerCommandRepository repository,
            DomainEventOutboxRepository outboxRepository
    ) {
        this.batchProducer = batchProducer;
        this.repository = repository;
        this.outboxRepository = outboxRepository;
    }

    @Transactional(transactionManager = "commandTransactionManager")
    public void invoke(PlayerLeaveAllCommand command) {
        final List<PlayerId> ids = repository.findIdsByTeamId(command.teamId());
        if (ids.isEmpty()) return;
        repository.leaveAll(command.teamId());

        final List<DomainEvent> events = DomainEvent.updateTypes(DomainEventQueue.COMMAND_PLAYER.getName(), ids.stream().map(PlayerId::value).toList());
        outboxRepository.createAll(events);
        batchProducer.sendBatch(events);
    }
}
