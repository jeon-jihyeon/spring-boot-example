package com.example.spring.domain.team;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventInbox;
import com.example.spring.domain.player.PlayerCommandRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamEventHandlerTest {
    @Mock
    private DomainEventInbox inbox;
    @Mock
    private PlayerCommandRepository playerRepository;
    @Mock
    private TeamQueryRepository repository;
    @Mock
    private TeamCommandApiClient client;
    @InjectMocks
    private TeamEventHandler handler;

    @Test
    void shouldPreventDuplicationForExistingEventInInbox() {
        when(inbox.exists(any(Long.class))).thenReturn(true);
        handler.handle(DomainEvent.createType("test", 1L));

        verify(inbox, never()).save(any());
        verifyNoInteractions(client);
        verifyNoInteractions(repository);
        verifyNoInteractions(playerRepository);
    }
}