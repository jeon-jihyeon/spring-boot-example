package com.example.spring.domain.team;

import com.example.spring.domain.BaseUnitTest;
import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventInbox;
import com.example.spring.domain.event.Layer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamQueryEventHandlerTest extends BaseUnitTest {
    @Mock
    private DomainEventInbox inbox;
    @Mock
    private TeamQueryRepository repository;
    @Mock
    private TeamCommandApiClient client;
    @InjectMocks
    private TeamQueryEventHandler handler;

    @Test
    void shouldPreventDuplicationForExistingEventInInbox() {
        when(inbox.exists(any(Long.class))).thenReturn(true);
        handler.handle(DomainEvent.createType(Layer.DOMAIN, "test", 1L));

        verify(inbox, never()).save(any());
        verifyNoInteractions(client);
        verifyNoInteractions(repository);
    }
}