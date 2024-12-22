package com.example.spring.infrastructure.db.outbox;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.infrastructure.db.BaseContextTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class DomainEventAdapterTest extends BaseContextTest {
    private final DomainEventAdapter adapter;

    public DomainEventAdapterTest(DomainEventAdapter adapter) {
        this.adapter = adapter;
    }

    @Test
    @DisplayName("DomainEvent DB 생성-조회-삭제 테스트")
    void shouldBeSavedAndFound() {
        final LocalDateTime now = LocalDateTime.now();
        adapter.save(new DomainEvent(1L, "modelName", 2L, now, false, now));

        final DomainEvent found = adapter.findById(1L);
        assertThat(found.id()).isEqualTo(1L);
        assertThat(found.modelName()).isEqualTo("modelName");
        assertThat(found.modelId()).isEqualTo(2L);
        assertThat(found.createdAt()).isEqualTo(now);
        assertThat(found.published()).isFalse();
        assertThat(found.publishedAt()).isEqualTo(now);
    }
}