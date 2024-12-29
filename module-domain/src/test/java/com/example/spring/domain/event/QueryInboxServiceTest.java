package com.example.spring.domain.event;

import com.example.spring.domain.event.dto.DomainEventCommand;
import com.example.spring.domain.event.dto.InboxCompleteEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QueryInboxServiceTest {
    @Mock
    private DomainEventInbox inbox;
    @InjectMocks
    private QueryInboxService service;

    @Test
    void shouldNotSaveWhenFindEventCausesException() {
        when(inbox.findEvent(any(Long.class))).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> service.complete(new InboxCompleteEvent(1L)));

        verify(inbox, never()).save(any());
        verify(inbox, times(1)).findEvent(any());
    }

    @Test
    @DisplayName("Inbox 중복 실행 방지 테스트")
    void shouldPreventDuplicationForExistingEventInInbox() {
        when(inbox.exists(any(Long.class))).thenReturn(true);
        service.receive(DomainEvent.createType(new DomainEventCommand("test", 1L)));

        verify(inbox, never()).save(any());
    }
}