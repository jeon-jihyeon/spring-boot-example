package com.example.spring.domain.player;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventOutbox;
import com.example.spring.domain.event.MessageProducer;
import com.example.spring.domain.player.model.PlayerId;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PlayerTeamMessageServiceTest {

    private static final PlayerId PLAYER_ID = new PlayerId(22L);
    @Mock
    private DomainEventOutbox outbox;
    @Mock
    private MessageProducer producer;
    @InjectMocks
    private PlayerTeamMessageService service;

    @Test
    void shouldNotSaveWhenExceptionOccursInCreate() {
        doThrow(RuntimeException.class).when(producer).send(any(DomainEvent.class));
        assertThrows(RuntimeException.class, () -> service.send(PLAYER_ID));

        verify(outbox, never()).save(any());
        verify(producer, times(1)).send(any());
    }
}