package com.example.derivation.application;

import com.example.core.enums.Timeframe;
import com.example.core.values.*;
import com.example.derivation.domain.Candle;
import com.example.derivation.domain.calculator.EmaCalculator;
import com.example.derivation.domain.calculator.MacdCalculator;
import com.example.derivation.domain.indicator.Code;
import com.example.derivation.domain.indicator.MacdParams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Currency;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetIndicatorsTest {

    @Mock
    private CandleFetcher candleFetcher;

    private GetIndicators getIndicators;

    @BeforeEach
    void setUp() {
        var emaCalculator = new EmaCalculator(12);
        var macdCalculator = new MacdCalculator(MacdParams.STANDARD);
        getIndicators = new GetIndicators(candleFetcher, emaCalculator, macdCalculator);
    }

    @Test
    @DisplayName("execute - EMA와 MACD 지표를 반환")
    void execute_returnsEmaAndMacd() {
        // given
        var request = new CandlesRequest(
                new Symbol("KRW-BTC"),
                Currency.getInstance("KRW"),
                new EpochMillis(1000L),
                new EpochMillis(50000L)
        );
        when(candleFetcher.find(any())).thenReturn(createCandles());

        // when
        var result = getIndicators.execute(request);

        // then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).code()).isEqualTo(Code.EMA);
        assertThat(result.get(1).code()).isEqualTo(Code.MACD);
    }

    @Test
    @DisplayName("execute - CandleFetcher에 올바른 request 전달")
    void execute_passesRequestToCandleFetcher() {
        // given
        var request = new CandlesRequest(
                new Symbol("KRW-ETH"),
                Currency.getInstance("USD"),
                new EpochMillis(5000L),
                new EpochMillis(10000L)
        );
        when(candleFetcher.find(any())).thenReturn(createCandles());

        // when
        getIndicators.execute(request);

        // then
        verify(candleFetcher).find(request);
    }

    private List<Candle> createCandles() {
        return IntStream.range(0, 50)
                .mapToObj(i -> new Candle(
                        new Symbol("KRW-BTC"),
                        new EpochMillis(1000L * (i + 1)),
                        createOHLCV(100.0 + i),
                        Timeframe.DAYS
                ))
                .toList();
    }

    private OHLCV createOHLCV(double closePrice) {
        return new OHLCV(
                Price.from(closePrice - 5),
                Price.from(closePrice + 5),
                Price.from(closePrice - 10),
                Price.from(closePrice),
                Volume.from(1000),
                Price.from(5000)
        );
    }
}
