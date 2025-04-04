package com.example.spring.domain.outbox;

import com.example.spring.domain.BaseUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class OutboxEventTest extends BaseUnitTest {
    @Test
    @DisplayName("DomainEvent 모델 초기화 테스트")
    void shouldInitializeModel() {
        final LocalDateTime now = LocalDateTime.now();
        final OutboxEvent model = new OutboxEvent(1L, false, OutboxEventType.ADD_POINT, 2L, now, now);
        assertThat(model.id()).isEqualTo(1L);
        assertThat(model.completed()).isFalse();
        assertThat(model.type()).isEqualTo(OutboxEventType.ADD_POINT);
        assertThat(model.entityId()).isEqualTo(2L);
        assertThat(model.createdAt()).isEqualTo(now);
        assertThat(model.completedAt()).isEqualTo(now);
    }
}