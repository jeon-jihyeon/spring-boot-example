package com.example.core.values;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EpochMillisTest {

    @Test
    @DisplayName("생성 - 유효한 값으로 생성")
    void constructor_validValue_createsEpochMillis() {
        // given
        var value = 1234567890L;

        // when
        var epochMillis = new EpochMillis(value);

        // then
        assertThat(epochMillis.value()).isEqualTo(value);
    }

    @Test
    @DisplayName("생성 - null 값 시 예외 발생")
    void constructor_nullValue_throwsException() {
        assertThatThrownBy(() -> new EpochMillis(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Timestamp cannot be null");
    }

    @Test
    @DisplayName("from(Long) - Long으로 생성")
    void fromLong_validLong_createsEpochMillis() {
        // when
        var epochMillis = EpochMillis.from(1234567890L);

        // then
        assertThat(epochMillis.value()).isEqualTo(1234567890L);
    }

    @Test
    @DisplayName("from(LocalDateTime) - LocalDateTime으로 생성")
    void fromLocalDateTime_validDateTime_createsEpochMillis() {
        // given
        var dateTime = LocalDateTime.of(2023, 1, 1, 12, 0, 0);
        var expectedMillis = dateTime.toInstant(ZoneOffset.UTC).toEpochMilli();

        // when
        var epochMillis = EpochMillis.from(dateTime);

        // then
        assertThat(epochMillis.value()).isEqualTo(expectedMillis);
    }

    @Test
    @DisplayName("toDateTime - LocalDateTime으로 변환")
    void toDateTime_returnsLocalDateTime() {
        // given
        var dateTime = LocalDateTime.of(2023, 6, 15, 10, 30, 45);
        var millis = dateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
        var epochMillis = new EpochMillis(millis);

        // when
        var result = epochMillis.toDateTime();

        // then
        assertThat(result).isEqualTo(dateTime);
    }

    @Test
    @DisplayName("from과 toDateTime 왕복 변환")
    void fromAndToDateTime_roundTrip_preservesValue() {
        // given
        var originalDateTime = LocalDateTime.of(2024, 12, 25, 15, 45, 30);

        // when
        var epochMillis = EpochMillis.from(originalDateTime);
        var resultDateTime = epochMillis.toDateTime();

        // then
        assertThat(resultDateTime).isEqualTo(originalDateTime);
    }
}
