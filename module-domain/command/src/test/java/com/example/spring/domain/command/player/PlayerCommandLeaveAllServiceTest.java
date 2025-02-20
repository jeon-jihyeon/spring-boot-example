package com.example.spring.domain.command.player;

import com.example.spring.domain.command.player.model.PlayerId;
import com.example.spring.domain.command.team.model.TeamId;
import com.example.spring.domain.event.DomainEventOutboxRepository;
import com.example.spring.domain.event.MessageBatchProducer;
import com.example.spring.domain.event.model.DomainEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerCommandLeaveAllServiceTest {
    private static final DomainEvent EVENT = DomainEvent.updateType("team", 1L);
    private static final List<PlayerId> PLAYER_IDS = List.of(new PlayerId(1L));
    @Mock
    private PlayerCommandRepository repository;
    @Mock
    private DomainEventOutboxRepository outboxRepository;
    @Mock
    private MessageBatchProducer batchProducer;
    @InjectMocks
    private PlayerCommandLeaveAllService service;

    @Test
    void shouldJoinToTeamAndReturnValidResponse() {
        when(repository.findIdsByTeamId(any(TeamId.class))).thenReturn(PLAYER_IDS);

        // then
        service.invoke(EVENT);
        verify(outboxRepository, times(1)).save(any());
        verify(repository, times(1)).leaveAll(any());
        verify(outboxRepository, times(1)).createAll(any());
        verify(batchProducer, times(1)).sendBatch(any());
    }

    @Test
    void shouldExitWhenPlayersNotFound() {
        when(repository.findIdsByTeamId(any(TeamId.class))).thenReturn(anyList());
        service.invoke(EVENT);

        // then
        verify(outboxRepository, times(1)).save(any());
        verify(repository, times(1)).findIdsByTeamId(any());
        verify(outboxRepository, never()).createAll(any());
        verify(repository, never()).leaveAll(any());
        verify(batchProducer, never()).sendBatch(any());
    }
}