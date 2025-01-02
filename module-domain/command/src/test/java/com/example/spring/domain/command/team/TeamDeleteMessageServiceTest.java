package com.example.spring.domain.command.team;

import com.example.spring.domain.command.team.dto.TeamDeleteEvent;
import com.example.spring.domain.command.team.model.TeamId;
import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventOutbox;
import com.example.spring.domain.event.MessageProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamDeleteMessageServiceTest {
    private static final TeamDeleteEvent EVENT = new TeamDeleteEvent(new TeamId(22L));
    @Mock
    private DomainEventOutbox outbox;
    @Mock
    private MessageProducer producer;
    @InjectMocks
    private TeamDeleteMessageService service;

    @Test
    void shouldNotSendWhenExceptionOccurs() {
        doThrow(RuntimeException.class).when(producer).send(any(DomainEvent.class));
        assertThrows(RuntimeException.class, () -> service.send(EVENT));

        verify(outbox, never()).save(any());
        verify(producer, times(1)).send(any());
    }
}