package com.example.spring.domain.command.player;

import com.example.spring.domain.command.player.model.PlayerId;
import com.example.spring.domain.command.team.model.TeamId;
import com.example.spring.domain.event.DomainEventOutboxRepository;
import com.example.spring.domain.event.MessageBatchProducer;
import com.example.spring.domain.event.model.DomainEvent;
import com.example.spring.domain.event.model.DomainEventQueue;
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
    public void invoke(DomainEvent event) {
        outboxRepository.save(event.complete());

        final TeamId teamId = new TeamId(event.modelId());
        final List<Long> playerIds = repository.findIdsByTeamId(teamId).stream().map(PlayerId::value).toList();
        if (playerIds.isEmpty()) return;
        repository.leaveAll(teamId);

        final List<DomainEvent> events = DomainEvent.updateTypes(DomainEventQueue.COMMAND_PLAYER.getName(), playerIds);
        outboxRepository.createAll(events);
        batchProducer.sendBatch(events);
    }
}
