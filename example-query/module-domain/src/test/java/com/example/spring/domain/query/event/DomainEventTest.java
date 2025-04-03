package com.example.spring.domain.query.event;

import com.example.spring.domain.BaseUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class DomainEventTest extends BaseUnitTest {
    @Test
    @DisplayName("DomainEvent 모델 초기화 테스트")
    void shouldInitializeModel() {
        final LocalDateTime now = LocalDateTime.now();
        final DomainEvent model = new DomainEvent(1L, false, DomainEventType.CREATE, "model", 2L, now, now);
        assertThat(model.id()).isEqualTo(1L);
        assertThat(model.completed()).isFalse();
        assertThat(model.type()).isEqualTo(DomainEventType.CREATE);
        assertThat(model.queueName()).isEqualTo("model");
        assertThat(model.modelId()).isEqualTo(2L);
        assertThat(model.createdAt()).isEqualTo(now);
        assertThat(model.completedAt()).isEqualTo(now);
    }
}