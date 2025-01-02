package com.example.spring.domain.command.player;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.EventAlreadyExistsException;
import com.example.spring.domain.event.QueryInboxService;
import com.example.spring.domain.event.QueryOutboxService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerCommandHandlerTest {
    private static final DomainEvent CREATE_EVENT = DomainEvent.createType("test", 1L);
    @Mock
    private QueryInboxService inboxService;
    @Mock
    private QueryOutboxService outboxService;
    @Mock
    private PlayerCommandRepository repository;
    @InjectMocks
    private PlayerCommandHandler handler;

    @Test
    void shouldNotHandleWhenInboxCausesException() {
        doThrow(EventAlreadyExistsException.class).when(inboxService).receive(any(DomainEvent.class));
        assertThrows(EventAlreadyExistsException.class, () -> handler.handle(CREATE_EVENT));

        verify(inboxService, times(1)).receive(any());
        verify(outboxService, never()).complete(any());
        verify(repository, never()).leaveAll(any());
        verify(inboxService, never()).complete(any());
    }

    @Test
    void shouldCallLeaveAll() {
        handler.handle(CREATE_EVENT);

        verify(inboxService, times(1)).receive(any());
        verify(outboxService, times(1)).complete(any());
        verify(repository, times(1)).leaveAll(any());
        verify(inboxService, times(1)).complete(any());
    }
}