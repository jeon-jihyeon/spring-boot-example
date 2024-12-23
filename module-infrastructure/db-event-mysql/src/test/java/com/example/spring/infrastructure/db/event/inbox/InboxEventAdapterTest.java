package com.example.spring.infrastructure.db.event.inbox;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.Layer;
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
        adapter.save(new DomainEvent(1L, Layer.DOMAIN, DomainEvent.Type.CREATE, "model", 2L, false, now, now));

        final InboxEventEntity found = repository.findById(1L).orElseThrow(RuntimeException::new);
        assertThat(found.getId()).isEqualTo(1L);
        assertThat(found.getLayer()).isEqualTo(Layer.DOMAIN);
        assertThat(found.getType()).isEqualTo(DomainEvent.Type.CREATE);
        assertThat(found.getModelName()).isEqualTo("model");
        assertThat(found.getModelId()).isEqualTo(2L);
        assertThat(found.getCreatedAt()).isEqualTo(now);
        assertThat(found.getCompleted()).isFalse();
        assertThat(found.getCompletedAt()).isEqualTo(now);
    }
}