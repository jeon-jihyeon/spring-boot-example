package com.example.app.advice;

import com.example.core.exception.BaseException;
import com.example.core.exception.ErrorResponse;
import com.example.core.exception.LogLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class LogAdviceTest {

    private final LogAdvice logAdvice = new LogAdvice();

    @Test
    @DisplayName("handleBaseException - ERROR 레벨은 500 반환")
    void handleBaseException_errorLevel_returns500() {
        // given
        var exception = new TestException("Error message", LogLevel.ERROR);

        // when
        var response = logAdvice.handleBaseException(exception);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().code()).isEqualTo("TestException");
        assertThat(response.getBody().message()).isEqualTo("Error message");
    }

    @Test
    @DisplayName("handleBaseException - WARN 레벨은 400 반환")
    void handleBaseException_warnLevel_returns400() {
        // given
        var exception = new TestException("Warn message", LogLevel.WARN);

        // when
        var response = logAdvice.handleBaseException(exception);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().message()).isEqualTo("Warn message");
    }

    @Test
    @DisplayName("handleBaseException - INFO 레벨은 400 반환")
    void handleBaseException_infoLevel_returns400() {
        // given
        var exception = new TestException("Info message", LogLevel.INFO);

        // when
        var response = logAdvice.handleBaseException(exception);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().message()).isEqualTo("Info message");
    }

    @Test
    @DisplayName("handleBaseException - DEBUG 레벨은 400 반환")
    void handleBaseException_debugLevel_returns400() {
        // given
        var exception = new TestException("Debug message", LogLevel.DEBUG);

        // when
        var response = logAdvice.handleBaseException(exception);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().message()).isEqualTo("Debug message");
    }

    @Test
    @DisplayName("handleBaseException - 응답 body에 예외 클래스명을 code로 포함")
    void handleBaseException_responseContainsExceptionClassName() {
        // given
        var exception = new TestException("test", LogLevel.WARN);

        // when
        var response = logAdvice.handleBaseException(exception);

        // then
        assertThat(response.getBody()).isEqualTo(new ErrorResponse("TestException", "test"));
    }

    private static class TestException extends BaseException {
        public TestException(String message, LogLevel logLevel) {
            super(message, logLevel);
        }
    }
}
