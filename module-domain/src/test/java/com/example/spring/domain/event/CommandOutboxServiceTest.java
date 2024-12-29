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
class CommandOutboxServiceTest extends BaseUnitTest {
    private static final DomainEventCommand COMMAND = new DomainEventCommand("name", 1L);
    @Mock
    private DomainEventOutbox outbox;
    @Mock
    private CommandMessageProducer producer;
    @InjectMocks
    private CommandOutboxService service;

    @Test
    void shouldNotSaveWhenExceptionOccursInCreate() {
        doThrow(RuntimeException.class).when(producer).send(any(DomainEvent.class));
        assertThrows(RuntimeException.class, () -> service.sendCreateType(COMMAND));

        verify(outbox, never()).save(any());
        verify(producer, times(1)).send(any());
    }

    @Test
    void shouldNotSaveWhenExceptionOccursInUpdate() {
        doThrow(RuntimeException.class).when(producer).send(any(DomainEvent.class));
        assertThrows(RuntimeException.class, () -> service.sendUpdateType(COMMAND));

        verify(outbox, never()).save(any());
        verify(producer, times(1)).send(any());
    }

    @Test
    void shouldNotSaveWhenExceptionOccursInDelete() {
        doThrow(RuntimeException.class).when(producer).send(any(DomainEvent.class));
        assertThrows(RuntimeException.class, () -> service.sendDeleteType(COMMAND));

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