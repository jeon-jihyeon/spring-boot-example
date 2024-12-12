package com.example.spring.domain.player;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PlayerIdTest {
    @Test
    @DisplayName("PlayerId 객체 초기화 테스트")
    void shouldGenerateValueForNewId() {
        final Long id = PlayerId.newId().value();
        assertThat(id).isNotNull();
        assertThat(id).isGreaterThan(0);
    }
}