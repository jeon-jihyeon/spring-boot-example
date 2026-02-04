package com.example.core.values;

import com.example.core.exception.InvalidValueException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PriceTest {

    @Test
    @DisplayName("생성 - 유효한 값으로 생성")
    void constructor_validValue_createsPrice() {
        // given
        var value = new BigDecimal("100.50");

        // when
        var price = new Price(value);

        // then
        assertThat(price.value()).isEqualByComparingTo(value);
    }

    @Test
    @DisplayName("생성 - null 값 시 예외 발생")
    void constructor_nullValue_throwsException() {
        assertThatThrownBy(() -> new Price(null))
                .isInstanceOf(InvalidValueException.class)
                .hasMessage("Price cannot be null");
    }

    @Test
    @DisplayName("생성 - 음수 값 시 예외 발생")
    void constructor_negativeValue_throwsException() {
        assertThatThrownBy(() -> new Price(new BigDecimal("-1")))
                .isInstanceOf(InvalidValueException.class)
                .hasMessage("Price cannot be negative");
    }

    @Test
    @DisplayName("생성 - 0 값은 유효")
    void constructor_zeroValue_createsPrice() {
        // when
        var price = new Price(BigDecimal.ZERO);

        // then
        assertThat(price.value()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("from(String) - 문자열로 생성")
    void fromString_validString_createsPrice() {
        // when
        var price = Price.from("123.456");

        // then
        assertThat(price.value()).isEqualByComparingTo("123.456");
    }

    @Test
    @DisplayName("from(double) - double로 생성")
    void fromDouble_validDouble_createsPrice() {
        // when
        var price = Price.from(123.456);

        // then
        assertThat(price.value()).isEqualByComparingTo("123.456");
    }

    @Test
    @DisplayName("zero - 0 값 반환")
    void zero_returnsZeroPrice() {
        // when
        var price = Price.zero();

        // then
        assertThat(price.value()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("isLessThan - 작은 경우 true 반환")
    void isLessThan_smallerValue_returnsTrue() {
        // given
        var price1 = Price.from("100");
        var price2 = Price.from("200");

        // then
        assertThat(price1.isLessThan(price2)).isTrue();
    }

    @Test
    @DisplayName("isLessThan - 큰 경우 false 반환")
    void isLessThan_largerValue_returnsFalse() {
        // given
        var price1 = Price.from("200");
        var price2 = Price.from("100");

        // then
        assertThat(price1.isLessThan(price2)).isFalse();
    }

    @Test
    @DisplayName("isLessThan - 같은 경우 false 반환")
    void isLessThan_equalValue_returnsFalse() {
        // given
        var price1 = Price.from("100");
        var price2 = Price.from("100");

        // then
        assertThat(price1.isLessThan(price2)).isFalse();
    }

    @Test
    @DisplayName("isGreaterThan - 큰 경우 true 반환")
    void isGreaterThan_largerValue_returnsTrue() {
        // given
        var price1 = Price.from("200");
        var price2 = Price.from("100");

        // then
        assertThat(price1.isGreaterThan(price2)).isTrue();
    }

    @Test
    @DisplayName("isGreaterThan - 작은 경우 false 반환")
    void isGreaterThan_smallerValue_returnsFalse() {
        // given
        var price1 = Price.from("100");
        var price2 = Price.from("200");

        // then
        assertThat(price1.isGreaterThan(price2)).isFalse();
    }

    @Test
    @DisplayName("isGreaterThan - 같은 경우 false 반환")
    void isGreaterThan_equalValue_returnsFalse() {
        // given
        var price1 = Price.from("100");
        var price2 = Price.from("100");

        // then
        assertThat(price1.isGreaterThan(price2)).isFalse();
    }

    @Test
    @DisplayName("add - 두 가격 합산")
    void add_twoPrices_returnsSum() {
        // given
        var price1 = Price.from("100.50");
        var price2 = Price.from("50.25");

        // when
        var result = price1.add(price2);

        // then
        assertThat(result.value()).isEqualByComparingTo("150.75");
    }
}
