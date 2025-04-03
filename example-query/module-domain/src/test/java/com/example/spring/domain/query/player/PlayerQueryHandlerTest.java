package com.example.spring.domain.query.player;

import com.example.spring.domain.BaseUnitTest;
import com.example.spring.domain.InboxQueryService;
import com.example.spring.domain.OutboxQueryApiClient;
import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.EventAlreadyExistsException;
import com.example.spring.domain.query.PlayerCommandApiClient;
import com.example.spring.domain.query.PlayerQueryHandler;
import com.example.spring.domain.query.PlayerQueryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerQueryHandlerTest extends BaseUnitTest {
    @Mock
    private InboxQueryService inboxService;
    @Mock
    private OutboxQueryApiClient outboxClient;
    @Mock
    private PlayerQueryRepository repository;
    @Mock
    private PlayerCommandApiClient client;
    @InjectMocks
    private PlayerQueryHandler handler;

    @Test
    void shouldNotHandleWhenInboxCausesException() {
        doThrow(EventAlreadyExistsException.class).when(inboxService).receive(any(DomainEvent.class));
        assertThrows(EventAlreadyExistsException.class, () -> handler.handle(any()));

        verify(inboxService, times(1)).receive(any());
        verify(outboxClient, never()).complete(any());
        verify(client, never()).findById(any());
        verify(repository, never()).create(any());
        verify(inboxService, never()).complete(any());
    }

    @Test
    void shouldCallSaveService() {
        handler.handle(any());

        verify(inboxService, times(1)).receive(any());
        verify(outboxClient, times(1)).complete(any());
        verify(client, times(1)).findById(any());
        verify(repository, times(1)).create(any());
        verify(repository, never()).deleteById(any());
        verify(inboxService, times(1)).complete(any());
    }

    @Test
    void shouldCallDeleteService() {
        handler.handle(any());

        verify(inboxService, times(1)).receive(any());
        verify(outboxClient, times(1)).complete(any());
        verify(repository, never()).create(any());
        verify(client, never()).findById(any());
        verify(repository, times(1)).deleteById(any());
        verify(inboxService, times(1)).complete(any());
    }
}