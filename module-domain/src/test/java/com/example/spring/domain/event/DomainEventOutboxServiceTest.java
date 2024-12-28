package com.example.spring.domain.event;

import com.example.spring.domain.BaseUnitTest;
import com.example.spring.domain.event.dto.DomainEventCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DomainEventOutboxServiceTest extends BaseUnitTest {
    private static final DomainEventCommand COMMAND = new DomainEventCommand("name", 1L);
    @Mock
    private DomainEventOutbox outbox;
    @Mock
    private DomainEventProducer producer;
    @InjectMocks
    private DomainEventOutboxService service;

    @Test
    void shouldCallSaveInCreate() {
        service.create(COMMAND);
        verify(outbox, times(1)).save(any());
    }

    @Test
    void shouldNotSaveWhenExceptionOccursInProcess() {
        when(outbox.findEvent(any(DomainEventCommand.class))).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> service.process(COMMAND));
        verify(outbox, never()).save(any());
    }

    @Test
    void shouldCallSaveInProcess() {
        when(outbox.findEvent(any(DomainEventCommand.class))).thenReturn(DomainEvent.createType(COMMAND));
        service.process(COMMAND);
        verify(outbox, times(1)).save(any());
    }

    @Test
    void shouldCallSaveInComplete() {
        service.create(COMMAND);
        verify(outbox, times(1)).save(any());
    }

    @Test
    void shouldNotSendWhenExceptionOccursInComplete() {
        doThrow(RuntimeException.class).when(outbox).save(any(DomainEvent.class));
        assertThrows(RuntimeException.class, () -> service.complete(DomainEvent.createType(COMMAND)));
        verify(outbox, times(1)).save(any());
        verify(producer, never()).send(any());
    }
}