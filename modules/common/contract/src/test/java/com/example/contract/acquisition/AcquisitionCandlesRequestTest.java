package com.example.contract.acquisition;

import com.example.core.exception.InvalidValueException;
import com.example.core.values.EpochMillis;
import com.example.core.values.Symbol;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AcquisitionCandlesRequestTest {

    @Test
    @DisplayName("생성 - 유효한 값으로 생성")
    void constructor_validValues_createsRequest() {
        // given
        var symbol = Symbol.from("BTC");
        var currency = Currency.getInstance("USD");
        var start = EpochMillis.from(1000L);
        var end = EpochMillis.from(2000L);

        // when
        var request = new AcquisitionCandlesRequest(symbol, currency, start, end);

        // then
        assertThat(request.symbol()).isEqualTo(symbol);
        assertThat(request.currency()).isEqualTo(currency);
        assertThat(request.start()).isEqualTo(start);
        assertThat(request.end()).isEqualTo(end);
    }

    @Test
    @DisplayName("생성 - symbol이 null이면 예외 발생")
    void constructor_nullSymbol_throwsException() {
        assertThatThrownBy(() -> new AcquisitionCandlesRequest(
                null,
                Currency.getInstance("USD"),
                EpochMillis.from(1000L),
                EpochMillis.from(2000L)
        ))
                .isInstanceOf(InvalidValueException.class)
                .hasMessage("symbol and range cannot be null");
    }

    @Test
    @DisplayName("생성 - currency가 null이면 예외 발생")
    void constructor_nullCurrency_throwsException() {
        assertThatThrownBy(() -> new AcquisitionCandlesRequest(
                Symbol.from("BTC"),
                null,
                EpochMillis.from(1000L),
                EpochMillis.from(2000L)
        ))
                .isInstanceOf(InvalidValueException.class)
                .hasMessage("symbol and range cannot be null");
    }

    @Test
    @DisplayName("생성 - start가 null이면 예외 발생")
    void constructor_nullStart_throwsException() {
        assertThatThrownBy(() -> new AcquisitionCandlesRequest(
                Symbol.from("BTC"),
                Currency.getInstance("USD"),
                null,
                EpochMillis.from(2000L)
        ))
                .isInstanceOf(InvalidValueException.class)
                .hasMessage("symbol and range cannot be null");
    }

    @Test
    @DisplayName("생성 - end가 null이면 예외 발생")
    void constructor_nullEnd_throwsException() {
        assertThatThrownBy(() -> new AcquisitionCandlesRequest(
                Symbol.from("BTC"),
                Currency.getInstance("USD"),
                EpochMillis.from(1000L),
                null
        ))
                .isInstanceOf(InvalidValueException.class)
                .hasMessage("symbol and range cannot be null");
    }
}
