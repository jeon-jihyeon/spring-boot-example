package com.example.spring.boot.modules.player.domain.model;

import com.example.spring.boot.core.exceptions.InvalidValueException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

class FullNameTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"1234567890123456789012345678901"})
    void test_validation(String errorValue) {
        assertThrows(InvalidValueException.class, () -> new FullName("first", errorValue));
        assertThrows(InvalidValueException.class, () -> new FullName(errorValue, "last"));
    }
}