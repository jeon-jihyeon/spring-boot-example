package com.example.spring.domain.team;

import com.example.spring.domain.BaseUnitTest;
import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventOutbox;
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
class TeamMessageServiceTest extends BaseUnitTest {
    private static final TeamId TEAM_ID = new TeamId(111L);
    @Mock
    private DomainEventOutbox outbox;
    @Mock
    private TeamMessageProducer producer;
    @InjectMocks
    private TeamMessageService service;

    @Test
    void shouldNotSaveWhenExceptionOccursInCreate() {
        doThrow(RuntimeException.class).when(producer).send(any(DomainEvent.class));
        assertThrows(RuntimeException.class, () -> service.sendCreateType(TEAM_ID));

        verify(outbox, never()).save(any());
        verify(producer, times(1)).send(any());
    }

    @Test
    void shouldNotSaveWhenExceptionOccursInUpdate() {
        doThrow(RuntimeException.class).when(producer).send(any(DomainEvent.class));
        assertThrows(RuntimeException.class, () -> service.sendUpdateType(TEAM_ID));

        verify(outbox, never()).save(any());
        verify(producer, times(1)).send(any());
    }

    @Test
    void shouldNotSaveWhenExceptionOccursInDelete() {
        doThrow(RuntimeException.class).when(producer).send(any(DomainEvent.class));
        assertThrows(RuntimeException.class, () -> service.sendDeleteType(TEAM_ID));

        verify(outbox, never()).save(any());
        verify(producer, times(1)).send(any());
    }
}