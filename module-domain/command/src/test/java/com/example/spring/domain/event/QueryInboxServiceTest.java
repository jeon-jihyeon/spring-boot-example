package com.example.spring.domain.event;

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
class QueryInboxServiceTest {
    private static final DomainEvent EVENT = DomainEvent.createType("test", 1L);
    @Mock
    private DomainEventInbox inbox;
    @InjectMocks
    private QueryInboxService service;

    @Test
    @DisplayName("Inbox 중복 실행 방지 테스트")
    void shouldPreventDuplicationForExistingEventInInbox() {
        when(inbox.exists(any(Long.class))).thenReturn(true);
        assertThrows(EventAlreadyExistsException.class, () -> service.receive(EVENT));
    }
}