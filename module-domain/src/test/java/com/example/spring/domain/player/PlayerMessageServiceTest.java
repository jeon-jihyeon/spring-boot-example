package com.example.spring.domain.player;

import com.example.spring.domain.BaseUnitTest;
import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventOutbox;
import com.example.spring.domain.player.model.PlayerId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerMessageServiceTest extends BaseUnitTest {
    private static final PlayerId PLAYER_ID = new PlayerId(22L);
    @Mock
    private DomainEventOutbox outbox;
    @Mock
    private PlayerMessageProducer producer;
    @InjectMocks
    private PlayerMessageService service;

    @Test
    void shouldNotSaveWhenExceptionOccursInCreate() {
        doThrow(RuntimeException.class).when(producer).send(any(DomainEvent.class));
        assertThrows(RuntimeException.class, () -> service.sendCreateType(PLAYER_ID));

        verify(outbox, never()).save(any());
        verify(producer, times(1)).send(any());
    }

    @Test
    void shouldNotSaveWhenExceptionOccursInUpdate() {
        doThrow(RuntimeException.class).when(producer).send(any(DomainEvent.class));
        assertThrows(RuntimeException.class, () -> service.sendUpdateType(PLAYER_ID));

        verify(outbox, never()).save(any());
        verify(producer, times(1)).send(any());
    }

    @Test
    void shouldNotSaveWhenExceptionOccursInDelete() {
        doThrow(RuntimeException.class).when(producer).send(any(DomainEvent.class));
        assertThrows(RuntimeException.class, () -> service.sendDeleteType(PLAYER_ID));

        verify(outbox, never()).save(any());
        verify(producer, times(1)).send(any());
    }
}