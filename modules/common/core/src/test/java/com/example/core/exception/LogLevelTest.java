package com.example.core.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LogLevelTest {

    @Test
    @DisplayName("모든 LogLevel 값 존재 확인")
    void values_containsAllExpectedValues() {
        assertThat(LogLevel.values())
                .containsExactly(LogLevel.INFO, LogLevel.DEBUG, LogLevel.WARN, LogLevel.ERROR);
    }

    @Test
    @DisplayName("valueOf - 문자열로 LogLevel 조회")
    void valueOf_validString_returnsLogLevel() {
        assertThat(LogLevel.valueOf("INFO")).isEqualTo(LogLevel.INFO);
        assertThat(LogLevel.valueOf("DEBUG")).isEqualTo(LogLevel.DEBUG);
        assertThat(LogLevel.valueOf("WARN")).isEqualTo(LogLevel.WARN);
        assertThat(LogLevel.valueOf("ERROR")).isEqualTo(LogLevel.ERROR);
    }
}
