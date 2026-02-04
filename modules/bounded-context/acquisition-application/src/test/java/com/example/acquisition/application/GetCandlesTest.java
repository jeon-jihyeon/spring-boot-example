package com.example.acquisition.application;

import com.example.acquisition.domain.Candle;
import com.example.core.enums.Timeframe;
import com.example.core.values.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Currency;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetCandlesTest {

    @Mock
    private CandlesFinder candlesFinder;

    @InjectMocks
    private GetCandles getCandles;

    @Test
    @DisplayName("execute - CandlesFinder에서 캔들 조회 후 aggregator로 집계")
    void execute_findsAndAggregatesCandles() {
        // given
        var symbol = Symbol.from("BTC");
        var currency = Currency.getInstance("USD");
        var start = EpochMillis.from(0L);
        var end = EpochMillis.from(3600000L);
        var request = new GetCandlesRequest(symbol, currency, start, end, Timeframe.HOURS);

        var foundCandles = List.of(
                createCandle(symbol, currency, 0L, Timeframe.MINUTES),
                createCandle(symbol, currency, 60000L, Timeframe.MINUTES)
        );

        when(candlesFinder.find(any())).thenReturn(foundCandles);

        // when
        var result = getCandles.execute(request);

        // then
        assertThat(result).hasSize(1);
        verify(candlesFinder).find(request.toPeriodCandlesQuery());
    }

    @Test
    @DisplayName("execute - CandlesFinder에 올바른 query 전달")
    void execute_passesCorrectQueryToCandlesFinder() {
        // given
        var symbol = Symbol.from("ETH");
        var currency = Currency.getInstance("KRW");
        var start = EpochMillis.from(1000L);
        var end = EpochMillis.from(5000L);
        var request = new GetCandlesRequest(symbol, currency, start, end, Timeframe.HOURS);

        when(candlesFinder.find(any())).thenReturn(List.of());

        // when
        getCandles.execute(request);

        // then
        var expectedQuery = new CandlesQuery(symbol, currency, start, end);
        verify(candlesFinder).find(expectedQuery);
    }

    private Candle createCandle(Symbol symbol, Currency currency, long epochMillis, Timeframe timeframe) {
        var ohlcv = new OHLCV(
                Price.from("100"),
                Price.from("150"),
                Price.from("90"),
                Price.from("120"),
                Volume.from("1000"),
                Price.from("5000")
        );
        return new Candle(symbol, currency, EpochMillis.from(epochMillis), ohlcv, timeframe);
    }
}
