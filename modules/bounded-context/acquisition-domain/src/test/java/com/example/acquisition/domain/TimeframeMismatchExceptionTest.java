package com.example.acquisition.domain;

import com.example.core.enums.Timeframe;
import com.example.core.exception.LogLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TimeframeMismatchExceptionTest {

    @Test
    @DisplayName("생성 - 올바른 메시지 생성")
    void constructor_createsCorrectMessage() {
        // when
        var exception = new TimeframeMismatchException(Timeframe.MINUTES, Timeframe.HOURS);

        // then
        assertThat(exception.getMessage())
                .isEqualTo("Timeframe mismatch: expected MINUTES, but was HOURS");
    }

    @Test
    @DisplayName("LogLevel - ERROR 반환")
    void getLogLevel_returnsError() {
        // when
        var exception = new TimeframeMismatchException(Timeframe.MINUTES, Timeframe.HOURS);

        // then
        assertThat(exception.getLogLevel()).isEqualTo(LogLevel.ERROR);
    }
}
