package com.example.spring.boot.modules.player.domain.model;

import com.example.spring.boot.core.exception.InvalidValueException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

class FullNameTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"1234567890123456789012345678901"})
    @DisplayName("FullName 유효성 검증 테스트")
    void test_validation(String errorValue) {
        assertThrows(InvalidValueException.class, () -> new FullName("first", errorValue));
        assertThrows(InvalidValueException.class, () -> new FullName(errorValue, "last"));
    }
}