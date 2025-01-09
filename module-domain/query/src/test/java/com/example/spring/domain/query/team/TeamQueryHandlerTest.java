package com.example.spring.domain.query.team;

import com.example.spring.domain.BaseUnitTest;
import com.example.spring.domain.command.team.TeamCommandApiClient;
import com.example.spring.domain.event.EventAlreadyExistsException;
import com.example.spring.domain.event.model.DomainEvent;
import com.example.spring.domain.query.InboxQueryService;
import com.example.spring.domain.query.OutboxQueryApiClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamQueryHandlerTest extends BaseUnitTest {
    private static final DomainEvent CREATE_EVENT = DomainEvent.createType("test", 1L);
    private static final DomainEvent DELETE_EVENT = DomainEvent.deleteType("test", 1L);
    @Mock
    private InboxQueryService inboxService;
    @Mock
    private OutboxQueryApiClient outboxClient;
    @Mock
    private TeamQueryRepository repository;
    @Mock
    private TeamCommandApiClient client;
    @InjectMocks
    private TeamQueryHandler handler;

    @Test
    void shouldNotHandleWhenInboxCausesException() {
        doThrow(EventAlreadyExistsException.class).when(inboxService).receive(any(DomainEvent.class));
        assertThrows(EventAlreadyExistsException.class, () -> handler.handle(CREATE_EVENT));

        verify(inboxService, times(1)).receive(any());
        verify(outboxClient, never()).complete(any());
        verify(client, never()).findById(any());
        verify(repository, never()).create(any());
        verify(inboxService, never()).complete(any());
    }

    @Test
    void shouldCallSaveService() {
        handler.handle(CREATE_EVENT);

        verify(inboxService, times(1)).receive(any());
        verify(outboxClient, times(1)).complete(any());
        verify(repository, times(1)).create(any());
        verify(client, times(1)).findById(any());
        verify(repository, never()).deleteById(any());
        verify(inboxService, times(1)).complete(any());
    }

    @Test
    void shouldCallDeleteService() {
        handler.handle(DELETE_EVENT);

        verify(inboxService, times(1)).receive(any());
        verify(outboxClient, times(1)).complete(any());
        verify(repository, never()).create(any());
        verify(client, never()).findById(any());
        verify(repository, times(1)).deleteById(any());
        verify(inboxService, times(1)).complete(any());
    }
}