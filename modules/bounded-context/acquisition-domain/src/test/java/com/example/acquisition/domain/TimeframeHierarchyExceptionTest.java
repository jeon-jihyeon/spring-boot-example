package com.example.acquisition.domain;

import com.example.core.enums.Timeframe;
import com.example.core.exception.LogLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TimeframeHierarchyExceptionTest {

    @Test
    @DisplayName("생성 - 올바른 메시지 생성")
    void constructor_createsCorrectMessage() {
        // when
        var exception = new TimeframeHierarchyException(Timeframe.HOURS, Timeframe.DAYS);

        // then
        assertThat(exception.getMessage())
                .isEqualTo("Timeframe hierarchy violation: expected a smaller timeframe than HOURS, but was DAYS");
    }

    @Test
    @DisplayName("LogLevel - ERROR 반환")
    void getLogLevel_returnsError() {
        // when
        var exception = new TimeframeHierarchyException(Timeframe.HOURS, Timeframe.DAYS);

        // then
        assertThat(exception.getLogLevel()).isEqualTo(LogLevel.ERROR);
    }
}
