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
        final DomainEvent model = new DomainEvent(1L, Layer.DOMAIN, DomainEvent.Type.CREATE, "model", 2L, false, now, now);
        assertThat(model.id()).isEqualTo(1L);
        assertThat(model.layer()).isEqualTo(Layer.DOMAIN);
        assertThat(model.type()).isEqualTo(DomainEvent.Type.CREATE);
        assertThat(model.modelName()).isEqualTo("model");
        assertThat(model.modelId()).isEqualTo(2L);
        assertThat(model.createdAt()).isEqualTo(now);
        assertThat(model.completed()).isFalse();
        assertThat(model.completedAt()).isEqualTo(now);
    }
}