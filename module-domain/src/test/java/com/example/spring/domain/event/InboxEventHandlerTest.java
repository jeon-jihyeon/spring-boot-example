package com.example.spring.domain.event;

import com.example.spring.domain.BaseUnitTest;
import com.example.spring.domain.event.dto.DomainEventCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InboxEventHandlerTest extends BaseUnitTest {
    @Mock
    private DomainEventInbox inbox;
    @InjectMocks
    private InboxEventHandler handler;

    @Test
    @DisplayName("Inbox 중복 실행 방지 테스트")
    void shouldPreventDuplicationForExistingEventInInbox() {
        when(inbox.exists(any(Long.class))).thenReturn(true);
        handler.handle(DomainEvent.createType(new DomainEventCommand("test", 1L)));

        verify(inbox, never()).save(any());
    }
}