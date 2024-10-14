package com.example.spring.boot.modules.player.domain;

import com.example.spring.boot.core.exceptions.InvalidValueException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class FullNameTest {

    @Test
    void test_validation() {
        assertThrows(InvalidValueException.class, () -> new FullName("first", null));
        assertThrows(InvalidValueException.class, () -> new FullName(null, "last"));
        assertThrows(InvalidValueException.class, () -> new FullName("1234567890123456789012345678901", null));
        assertThrows(InvalidValueException.class, () -> new FullName(null, "1234567890123456789012345678901"));
    }
}