package com.example.app.advice;

import com.example.core.exception.BaseException;
import com.example.core.exception.LogLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LogAdviceTest {

    private final LogAdvice logAdvice = new LogAdvice();

    @ParameterizedTest
    @EnumSource(LogLevel.class)
    @DisplayName("handleBaseException - 각 LogLevel별로 예외 처리 후 재던지기")
    void handleBaseException_allLogLevels_logsAndRethrows(LogLevel logLevel) {
        // given
        var exception = new TestException("Test message", logLevel);

        // when & then
        assertThatThrownBy(() -> logAdvice.handleBaseException(exception))
                .isSameAs(exception);
    }

    @Test
    @DisplayName("handleBaseException - INFO 레벨 로깅")
    void handleBaseException_infoLevel_logsInfo() {
        // given
        var exception = new TestException("Info message", LogLevel.INFO);

        // when & then
        assertThatThrownBy(() -> logAdvice.handleBaseException(exception))
                .isInstanceOf(TestException.class)
                .hasMessage("Info message");
    }

    @Test
    @DisplayName("handleBaseException - DEBUG 레벨 로깅")
    void handleBaseException_debugLevel_logsDebug() {
        // given
        var exception = new TestException("Debug message", LogLevel.DEBUG);

        // when & then
        assertThatThrownBy(() -> logAdvice.handleBaseException(exception))
                .isInstanceOf(TestException.class)
                .hasMessage("Debug message");
    }

    @Test
    @DisplayName("handleBaseException - WARN 레벨 로깅")
    void handleBaseException_warnLevel_logsWarn() {
        // given
        var exception = new TestException("Warn message", LogLevel.WARN);

        // when & then
        assertThatThrownBy(() -> logAdvice.handleBaseException(exception))
                .isInstanceOf(TestException.class)
                .hasMessage("Warn message");
    }

    @Test
    @DisplayName("handleBaseException - ERROR 레벨 로깅")
    void handleBaseException_errorLevel_logsError() {
        // given
        var exception = new TestException("Error message", LogLevel.ERROR);

        // when & then
        assertThatThrownBy(() -> logAdvice.handleBaseException(exception))
                .isInstanceOf(TestException.class)
                .hasMessage("Error message");
    }

    private static class TestException extends BaseException {
        public TestException(String message, LogLevel logLevel) {
            super(message, logLevel);
        }
    }
}
