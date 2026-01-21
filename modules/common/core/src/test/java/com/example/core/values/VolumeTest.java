package com.example.core.values;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VolumeTest {

    @Test
    @DisplayName("생성 - 유효한 값으로 생성")
    void constructor_validValue_createsVolume() {
        // given
        var value = new BigDecimal("1000.50");

        // when
        var volume = new Volume(value);

        // then
        assertThat(volume.value()).isEqualByComparingTo(value);
    }

    @Test
    @DisplayName("생성 - null 값 시 예외 발생")
    void constructor_nullValue_throwsException() {
        assertThatThrownBy(() -> new Volume(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Volume cannot be null");
    }

    @Test
    @DisplayName("생성 - 음수 값 시 예외 발생")
    void constructor_negativeValue_throwsException() {
        assertThatThrownBy(() -> new Volume(new BigDecimal("-1")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Volume cannot be negative");
    }

    @Test
    @DisplayName("생성 - 0 값은 유효")
    void constructor_zeroValue_createsVolume() {
        // when
        var volume = new Volume(BigDecimal.ZERO);

        // then
        assertThat(volume.value()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("from(String) - 문자열로 생성")
    void fromString_validString_createsVolume() {
        // when
        var volume = Volume.from("123.456");

        // then
        assertThat(volume.value()).isEqualByComparingTo("123.456");
    }

    @Test
    @DisplayName("from(double) - double로 생성")
    void fromDouble_validDouble_createsVolume() {
        // when
        var volume = Volume.from(123.456);

        // then
        assertThat(volume.value()).isEqualByComparingTo("123.456");
    }

    @Test
    @DisplayName("zero - 0 값 반환")
    void zero_returnsZeroVolume() {
        // when
        var volume = Volume.zero();

        // then
        assertThat(volume.value()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("add - 두 거래량 합산")
    void add_twoVolumes_returnsSum() {
        // given
        var volume1 = Volume.from("100.50");
        var volume2 = Volume.from("50.25");

        // when
        var result = volume1.add(volume2);

        // then
        assertThat(result.value()).isEqualByComparingTo("150.75");
    }
}
