package com.example.derivation.api;

import com.example.core.exception.InvalidValueException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class IndicatorRequestTest {

    @Test
    @DisplayName("생성 - 유효한 값으로 생성")
    void constructor_validValues_createsRequest() {
        // given & when
        var request = new IndicatorRequest("BTC", "USD", 1000L, 2000L);

        // then
        assertThat(request.symbol()).isEqualTo("BTC");
        assertThat(request.currency()).isEqualTo("USD");
        assertThat(request.start()).isEqualTo(1000L);
        assertThat(request.end()).isEqualTo(2000L);
    }

    @Test
    @DisplayName("생성 - symbol이 null이면 예외")
    void constructor_nullSymbol_throwsException() {
        assertThatThrownBy(() -> new IndicatorRequest(null, "USD", 1000L, 2000L))
                .isInstanceOf(InvalidValueException.class);
    }

    @Test
    @DisplayName("생성 - symbol이 빈 문자열이면 예외")
    void constructor_blankSymbol_throwsException() {
        assertThatThrownBy(() -> new IndicatorRequest(" ", "USD", 1000L, 2000L))
                .isInstanceOf(InvalidValueException.class);
    }

    @Test
    @DisplayName("생성 - currency가 null이면 예외")
    void constructor_nullCurrency_throwsException() {
        assertThatThrownBy(() -> new IndicatorRequest("BTC", null, 1000L, 2000L))
                .isInstanceOf(InvalidValueException.class);
    }

    @Test
    @DisplayName("생성 - start가 end보다 크면 예외")
    void constructor_startAfterEnd_throwsException() {
        assertThatThrownBy(() -> new IndicatorRequest("BTC", "USD", 2000L, 1000L))
                .isInstanceOf(InvalidValueException.class);
    }
}
