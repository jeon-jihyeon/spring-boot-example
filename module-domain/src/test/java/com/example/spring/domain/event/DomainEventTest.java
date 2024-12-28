package com.example.spring.domain.event;

import com.example.spring.domain.BaseUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class DomainEventTest extends BaseUnitTest {
    @Test
    @DisplayName("DomainEvent 모델 초기화 테스트")
    void shouldInitializeModel() {
        final LocalDateTime now = LocalDateTime.now();
        final DomainEvent model = new DomainEvent(1L, DomainEventState.CREATED, DomainEventType.CREATE, "Model", 2L, now, now, now);
        assertThat(model.id()).isEqualTo(1L);
        assertThat(model.state()).isEqualTo(DomainEventState.CREATED);
        assertThat(model.type()).isEqualTo(DomainEventType.CREATE);
        assertThat(model.modelName()).isEqualTo("model");
        assertThat(model.modelId()).isEqualTo(2L);
        assertThat(model.createdAt()).isEqualTo(now);
        assertThat(model.processedAt()).isEqualTo(now);
        assertThat(model.completedAt()).isEqualTo(now);
    }
}