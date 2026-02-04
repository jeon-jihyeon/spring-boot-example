package com.example.core.values;

import com.example.core.exception.InvalidValueException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AmountTest {

    @Test
    @DisplayName("생성 - 유효한 값으로 생성")
    void constructor_validValue_createsAmount() {
        // given
        var value = new BigDecimal("100.50");

        // when
        var amount = new Amount(value);

        // then
        assertThat(amount.value()).isEqualByComparingTo(value);
    }

    @Test
    @DisplayName("생성 - null 값 시 예외 발생")
    void constructor_nullValue_throwsException() {
        assertThatThrownBy(() -> new Amount(null))
                .isInstanceOf(InvalidValueException.class)
                .hasMessage("Amount cannot be null");
    }

    @Test
    @DisplayName("생성 - 음수 값 시 예외 발생")
    void constructor_negativeValue_throwsException() {
        assertThatThrownBy(() -> new Amount(new BigDecimal("-1")))
                .isInstanceOf(InvalidValueException.class)
                .hasMessage("Amount cannot be negative");
    }

    @Test
    @DisplayName("생성 - 0 값은 유효")
    void constructor_zeroValue_createsAmount() {
        // when
        var amount = new Amount(BigDecimal.ZERO);

        // then
        assertThat(amount.value()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("from(String) - 문자열로 생성")
    void fromString_validString_createsAmount() {
        // when
        var amount = Amount.from("123.456");

        // then
        assertThat(amount.value()).isEqualByComparingTo("123.456");
    }

    @Test
    @DisplayName("from(double) - double로 생성")
    void fromDouble_validDouble_createsAmount() {
        // when
        var amount = Amount.from(123.456);

        // then
        assertThat(amount.value()).isEqualByComparingTo("123.456");
    }

    @Test
    @DisplayName("zero - 0 값 반환")
    void zero_returnsZeroAmount() {
        // when
        var amount = Amount.zero();

        // then
        assertThat(amount.value()).isEqualByComparingTo(BigDecimal.ZERO);
    }
}
