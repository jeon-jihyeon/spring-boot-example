package com.example.core.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BaseExceptionTest {

    @Test
    @DisplayName("생성 - 메시지와 로그레벨 설정")
    void constructor_setsMessageAndLogLevel() {
        // given
        var message = "Test error message";
        var logLevel = LogLevel.ERROR;

        // when
        var exception = new TestException(message, logLevel);

        // then
        assertThat(exception.getMessage()).isEqualTo(message);
        assertThat(exception.getLogLevel()).isEqualTo(logLevel);
    }

    @Test
    @DisplayName("생성 - 각 LogLevel별로 예외 생성")
    void constructor_allLogLevels_createsException() {
        for (LogLevel level : LogLevel.values()) {
            var exception = new TestException("message", level);
            assertThat(exception.getLogLevel()).isEqualTo(level);
        }
    }

    private static class TestException extends BaseException {
        public TestException(String message, LogLevel logLevel) {
            super(message, logLevel);
        }
    }
}
