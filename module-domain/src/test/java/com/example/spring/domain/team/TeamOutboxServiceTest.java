package com.example.spring.domain.team;

import com.example.spring.domain.event.CommandMessageProducer;
import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventOutbox;
import com.example.spring.domain.event.dto.DomainEventCommand;
import com.example.spring.domain.team.dto.TeamCreateEvent;
import com.example.spring.domain.team.model.TeamId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamOutboxServiceTest {
    private static final DomainEventCommand COMMAND = new DomainEventCommand("name", 1L);
    private static final TeamCreateEvent CREATE_EVENT = new TeamCreateEvent(new TeamId(111L));
    @Mock
    private DomainEventOutbox outbox;
    @Mock
    private CommandMessageProducer producer;
    @InjectMocks
    private TeamOutboxService service;

    @Test
    void shouldNotSaveWhenExceptionOccursInCreate() {
        doThrow(RuntimeException.class).when(producer).send(any(DomainEvent.class));
        assertThrows(RuntimeException.class, () -> service.sendCreateType(CREATE_EVENT));

        verify(outbox, never()).save(any());
        verify(producer, times(1)).send(any());
    }

    @Test
    void shouldNotSaveWhenFindEventCausesException() {
        when(outbox.findEvent(any(DomainEventCommand.class))).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> service.complete(COMMAND));

        verify(outbox, never()).save(any());
        verify(outbox, times(1)).findEvent(any());
    }
}