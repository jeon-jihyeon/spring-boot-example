package com.example.spring.domain.event;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class DomainEventTest {
    @Test
    @DisplayName("DomainEvent 모델 초기화 테스트")
    void shouldInitializeModel() {
        final LocalDateTime now = LocalDateTime.now();
        final DomainEvent model = new DomainEvent(1L, "modelName", 2L, now, false, now);
        assertThat(model.id()).isEqualTo(1L);
        assertThat(model.modelName()).isEqualTo("modelName");
        assertThat(model.modelId()).isEqualTo(2L);
        assertThat(model.createdAt()).isEqualTo(now);
        assertThat(model.published()).isFalse();
        assertThat(model.publishedAt()).isEqualTo(now);
    }
}