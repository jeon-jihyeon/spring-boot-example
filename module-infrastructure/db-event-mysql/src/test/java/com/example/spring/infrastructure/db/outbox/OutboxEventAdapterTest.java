package com.example.spring.infrastructure.db.outbox;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.infrastructure.db.BaseCommandDbTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class OutboxEventAdapterTest extends BaseCommandDbTest {
    private final OutboxEventAdapter adapter;
    private final OutboxEventJpaRepository repository;

    public OutboxEventAdapterTest(OutboxEventAdapter adapter, OutboxEventJpaRepository repository) {
        this.adapter = adapter;
        this.repository = repository;
    }

    @Test
    @DisplayName("OutboxEvent DB 생성-조회-삭제 테스트")
    void shouldBeSavedAndFound() {
        final LocalDateTime now = LocalDateTime.now();
        adapter.save(new DomainEvent(1L, DomainEvent.Type.CREATE, "modelName", 2L, false, now, now));

        final OutboxEventEntity found = repository.findById(1L).orElseThrow(RuntimeException::new);
        assertThat(found.getId()).isEqualTo(1L);
        assertThat(found.getType()).isEqualTo(DomainEvent.Type.CREATE);
        assertThat(found.getModelName()).isEqualTo("modelName");
        assertThat(found.getModelId()).isEqualTo(2L);
        assertThat(found.getCreatedAt()).isEqualTo(now);
        assertThat(found.getCompleted()).isFalse();
        assertThat(found.getCompletedAt()).isEqualTo(now);
    }
}