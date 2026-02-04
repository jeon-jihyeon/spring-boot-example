package com.example.derivation.application;

import com.example.core.exception.InvalidValueException;
import com.example.core.values.EpochMillis;
import com.example.core.values.Symbol;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CandlesRequestTest {

    private static final Symbol SYMBOL = new Symbol("KRW-BTC");
    private static final Currency CURRENCY = Currency.getInstance("KRW");
    private static final EpochMillis START = new EpochMillis(1000L);
    private static final EpochMillis END = new EpochMillis(2000L);

    @Test
    @DisplayName("정상 생성")
    void create_success() {
        var request = new CandlesRequest(SYMBOL, CURRENCY, START, END);

        assertThat(request.symbol()).isEqualTo(SYMBOL);
        assertThat(request.currency()).isEqualTo(CURRENCY);
        assertThat(request.start()).isEqualTo(START);
        assertThat(request.end()).isEqualTo(END);
    }

    @Test
    @DisplayName("symbol이 null이면 예외 발생")
    void create_nullSymbol_throwsException() {
        assertThatThrownBy(() -> new CandlesRequest(null, CURRENCY, START, END))
                .isInstanceOf(InvalidValueException.class)
                .hasMessage("symbol and range cannot be null");
    }

    @Test
    @DisplayName("currency가 null이면 예외 발생")
    void create_nullCurrency_throwsException() {
        assertThatThrownBy(() -> new CandlesRequest(SYMBOL, null, START, END))
                .isInstanceOf(InvalidValueException.class)
                .hasMessage("symbol and range cannot be null");
    }

    @Test
    @DisplayName("start가 null이면 예외 발생")
    void create_nullStart_throwsException() {
        assertThatThrownBy(() -> new CandlesRequest(SYMBOL, CURRENCY, null, END))
                .isInstanceOf(InvalidValueException.class)
                .hasMessage("symbol and range cannot be null");
    }

    @Test
    @DisplayName("end가 null이면 예외 발생")
    void create_nullEnd_throwsException() {
        assertThatThrownBy(() -> new CandlesRequest(SYMBOL, CURRENCY, START, null))
                .isInstanceOf(InvalidValueException.class)
                .hasMessage("symbol and range cannot be null");
    }
}
