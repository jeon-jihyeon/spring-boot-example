package com.example.spring.boot.modules.player.domain.model;

import com.example.spring.boot.core.exception.InvalidValueException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PlayerIdTest {

    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {-1L})
    @DisplayName("PlayerId 유효성 검증")
    void test_validation(Long errorValue) {
        assertThrows(InvalidValueException.class, () -> new PlayerId(errorValue));
    }

    @Test
    @DisplayName("PlayerId 객체 초기화 검증")
    void test_newId_should_generate_id() {
        final Long id = PlayerId.newId().value();
        assertThat(id).isNotNull();
        assertThat(id).isGreaterThan(0);
    }
}