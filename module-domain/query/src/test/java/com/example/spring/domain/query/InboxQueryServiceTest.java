package com.example.spring.domain.query;

import com.example.spring.domain.BaseUnitTest;
import com.example.spring.domain.event.DomainEventInboxRepository;
import com.example.spring.domain.event.EventAlreadyExistsException;
import com.example.spring.domain.event.model.DomainEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InboxQueryServiceTest extends BaseUnitTest {
    private static final DomainEvent EVENT = DomainEvent.createType("test", 1L);
    @Mock
    private DomainEventInboxRepository inbox;
    @InjectMocks
    private InboxQueryService service;

    @Test
    @DisplayName("Inbox 중복 실행 방지 테스트")
    void shouldPreventDuplicationForExistingEventInInbox() {
        when(inbox.exists(any(Long.class))).thenReturn(true);
        assertThrows(EventAlreadyExistsException.class, () -> service.receive(EVENT));
    }
}