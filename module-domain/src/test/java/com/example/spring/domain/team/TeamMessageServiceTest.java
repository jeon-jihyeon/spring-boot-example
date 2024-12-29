package com.example.spring.domain.team;

import com.example.spring.domain.event.CommandMessageProducer;
import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventOutbox;
import com.example.spring.domain.event.dto.DomainEventCommand;
import com.example.spring.domain.team.dto.TeamCreateEvent;
import com.example.spring.domain.team.dto.TeamDeleteEvent;
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
class TeamMessageServiceTest {
    private static final DomainEventCommand COMMAND = new DomainEventCommand("name", 1L);
    private static final TeamCreateEvent CREATE_EVENT = new TeamCreateEvent(new TeamId(111L));
    private static final TeamDeleteEvent DELETE_EVENT = new TeamDeleteEvent(new TeamId(111L));
    @Mock
    private DomainEventOutbox outbox;
    @Mock
    private CommandMessageProducer producer;
    @InjectMocks
    private TeamMessageService service;

    @Test
    void shouldNotSaveWhenExceptionOccursInCreate() {
        doThrow(RuntimeException.class).when(producer).send(any(DomainEvent.class));
        assertThrows(RuntimeException.class, () -> service.sendCreateType(CREATE_EVENT));

        verify(outbox, never()).save(any());
    }

    @Test
    void shouldNotSaveWhenExceptionOccursInDelete() {
        doThrow(RuntimeException.class).when(producer).send(any(DomainEvent.class));
        assertThrows(RuntimeException.class, () -> service.sendDeleteType(DELETE_EVENT));

        verify(outbox, never()).save(any());
    }
}