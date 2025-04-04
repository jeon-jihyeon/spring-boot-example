package com.example.spring.domain.query.event;

import com.example.spring.domain.BaseUnitTest;
import com.example.spring.domain.event.InboxEvent;
import com.example.spring.domain.event.InboxEventType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class InboxEventTest extends BaseUnitTest {
    @Test
    @DisplayName("InboxEvent 모델 초기화 테스트")
    void shouldInitializeModel() {
        final LocalDateTime now = LocalDateTime.now();
        final InboxEvent model = new InboxEvent(1L, false, InboxEventType.PLAYER_CREATED, 2L, now, now);
        assertThat(model.id()).isEqualTo(1L);
        assertThat(model.completed()).isFalse();
        assertThat(model.type()).isEqualTo(InboxEventType.PLAYER_CREATED);
        assertThat(model.entityId()).isEqualTo(2L);
        assertThat(model.createdAt()).isEqualTo(now);
        assertThat(model.completedAt()).isEqualTo(now);
    }
}