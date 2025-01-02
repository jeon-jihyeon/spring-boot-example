package com.example.spring.domain.command.player;

import com.example.spring.domain.BaseUnitTest;
import com.example.spring.domain.command.player.model.PlayerId;
import com.example.spring.domain.event.DomainEventOutbox;
import com.example.spring.domain.event.DomainEventType;
import com.example.spring.domain.event.MessageBatchProducer;
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
class PlayerCommandMessageServiceTest extends BaseUnitTest {
    private static final PlayerId PLAYER_ID = new PlayerId(22L);
    @Mock
    private DomainEventOutbox outbox;
    @Mock
    private MessageProducer producer;
    @Mock
    private MessageBatchProducer batchProducer;
    @InjectMocks
    private PlayerCommandMessageService service;

    @Test
    void shouldNotSendWhenExceptionOccurs() {
        doThrow(RuntimeException.class).when(producer).send(any());
        assertThrows(RuntimeException.class, () -> service.send(DomainEventType.CREATE, PLAYER_ID));

        verify(outbox, never()).save(any());
        verify(producer, times(1)).send(any());
    }

    @Test
    void shouldNotSendBatchWhenExceptionOccurs() {
        doThrow(RuntimeException.class).when(batchProducer).sendBatch(any());
        assertThrows(RuntimeException.class, () -> service.sendBatch(any()));

        verify(outbox, never()).save(any());
        verify(batchProducer, times(1)).sendBatch(any());
    }
}