package com.example.acquisition.application;

import com.example.core.values.EpochMillis;
import com.example.core.values.Symbol;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;

class GetCandlesRequestTest {

    @Test
    @DisplayName("생성 - 유효한 값으로 생성")
    void constructor_validValues_createsRequest() {
        // given
        var symbol = Symbol.from("BTC");
        var currency = Currency.getInstance("USD");
        var start = EpochMillis.from(1000L);
        var end = EpochMillis.from(2000L);

        // when
        var request = new GetCandlesRequest(symbol, currency, start, end);

        // then
        assertThat(request.symbol()).isEqualTo(symbol);
        assertThat(request.currency()).isEqualTo(currency);
        assertThat(request.start()).isEqualTo(start);
        assertThat(request.end()).isEqualTo(end);
    }

    @Test
    @DisplayName("toPeriodCandlesQuery - CandlesQuery로 변환")
    void toPeriodCandlesQuery_convertsToCandlesQuery() {
        // given
        var symbol = Symbol.from("ETH");
        var currency = Currency.getInstance("KRW");
        var start = EpochMillis.from(5000L);
        var end = EpochMillis.from(10000L);
        var request = new GetCandlesRequest(symbol, currency, start, end);

        // when
        var query = request.toPeriodCandlesQuery();

        // then
        assertThat(query.symbol()).isEqualTo(symbol);
        assertThat(query.currency()).isEqualTo(currency);
        assertThat(query.start()).isEqualTo(start);
        assertThat(query.end()).isEqualTo(end);
    }
}
