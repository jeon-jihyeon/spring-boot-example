package com.example.acquisition.application;

import com.example.core.values.EpochMillis;
import com.example.core.values.Symbol;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;

class CandlesQueryTest {

    @Test
    @DisplayName("생성 - 유효한 값으로 생성")
    void constructor_validValues_createsQuery() {
        // given
        var symbol = Symbol.from("BTC");
        var currency = Currency.getInstance("USD");
        var start = EpochMillis.from(1000L);
        var end = EpochMillis.from(2000L);

        // when
        var query = new CandlesQuery(symbol, currency, start, end);

        // then
        assertThat(query.symbol()).isEqualTo(symbol);
        assertThat(query.currency()).isEqualTo(currency);
        assertThat(query.start()).isEqualTo(start);
        assertThat(query.end()).isEqualTo(end);
    }
}
