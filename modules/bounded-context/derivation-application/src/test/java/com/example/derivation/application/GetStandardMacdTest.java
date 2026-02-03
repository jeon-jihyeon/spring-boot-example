package com.example.derivation.application;

import com.example.core.enums.Timeframe;
import com.example.core.values.*;
import com.example.derivation.domain.Candle;
import com.example.derivation.domain.indicator.Code;
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
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetStandardMacdTest {

    @Mock
    private CandleFetcher candleFetcher;

    private GetStandardMacd getStandardMacd;

    @BeforeEach
    void setUp() {
        getStandardMacd = new GetStandardMacd(candleFetcher);
    }

    @Test
    @DisplayName("execute - 표준 MACD 지표를 반환")
    void execute_returnsStandardMacd() {
        // given
        var param = new IndicatorParam(
                new Symbol("KRW-BTC"),
                Currency.getInstance("KRW"),
                new EpochMillis(1000L),
                new EpochMillis(50000L)
        );
        when(candleFetcher.find(argThat(q -> q.symbol().value().equals("KRW-BTC"))))
                .thenReturn(createCandles());

        // when
        var result = getStandardMacd.execute(param);

        // then
        assertThat(result.code()).isEqualTo(Code.MACD);
    }

    @Test
    @DisplayName("execute - CandleFetcher에 올바른 CandlesRequest 전달")
    void execute_passesCandlesRequestToCandleFetcher() {
        // given
        var param = new IndicatorParam(
                new Symbol("KRW-ETH"),
                Currency.getInstance("USD"),
                new EpochMillis(5000L),
                new EpochMillis(10000L)
        );
        when(candleFetcher.find(argThat(q -> q.symbol().value().equals("KRW-ETH"))))
                .thenReturn(createCandles());

        // when
        getStandardMacd.execute(param);

        // then
        verify(candleFetcher).find(argThat(query -> {
            assertThat(query.symbol().value()).isEqualTo("KRW-ETH");
            assertThat(query.currency()).isEqualTo(Currency.getInstance("USD"));
            assertThat(query.start().value()).isEqualTo(5000L);
            assertThat(query.end().value()).isEqualTo(10000L);
            return true;
        }));
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
