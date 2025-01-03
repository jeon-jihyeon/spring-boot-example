package com.example.spring.infrastructure.db.event.inbox;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventType;
import com.example.spring.infrastructure.db.event.BaseContextTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class InboxEventAdapterTest extends BaseContextTest {
    private final InboxEventAdapter adapter;
    private final InboxEventJpaRepository repository;

    public InboxEventAdapterTest(InboxEventAdapter adapter, InboxEventJpaRepository repository) {
        this.adapter = adapter;
        this.repository = repository;
    }

    @Test
    @DisplayName("InboxEvent DB 생성-조회-삭제 테스트")
    void shouldBeSavedAndFound() {
        final LocalDateTime now = LocalDateTime.now();
        adapter.save(new DomainEvent(1L, false, DomainEventType.CREATE, "model", 2L, now, now));

        final InboxEventEntity found = repository.findById(1L).orElseThrow(RuntimeException::new);
        assertThat(found.getId()).isEqualTo(1L);
        assertThat(found.getCompleted()).isFalse();
        assertThat(found.getType()).isEqualTo(DomainEventType.CREATE);
        assertThat(found.getQueueName()).isEqualTo("model");
        assertThat(found.getModelId()).isEqualTo(2L);
        assertThat(found.getCreatedAt()).isEqualTo(now);
        assertThat(found.getCompletedAt()).isEqualTo(now);
    }
}