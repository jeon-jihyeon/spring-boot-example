package com.example.acquisition.infra;

import com.example.acquisition.application.CandlesQuery;
import com.example.acquisition.domain.Candle;
import com.example.core.enums.Timeframe;
import com.example.core.values.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Currency;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CandleJpaAdapterTest {

    @Mock
    private CandleJpaRepository jpaRepository;

    private CandleJpaAdapter candleJpaAdapter;

    @BeforeEach
    void setUp() {
        candleJpaAdapter = new CandleJpaAdapter(jpaRepository);
    }

    @Test
    @DisplayName("find - 조회 결과를 모델로 변환하여 반환")
    void find_returnsConvertedModels() {
        // given
        var symbol = Symbol.from("BTC");
        var currency = Currency.getInstance("USD");
        var start = EpochMillis.from(0L);
        var end = EpochMillis.from(3600000L);
        var query = new CandlesQuery(symbol, currency, start, end);

        var entities = List.of(
                createEntity("BTC", "USD", 0L),
                createEntity("BTC", "USD", 60000L)
        );
        when(jpaRepository.findAllBySymbolAndCurrencyAndStartTimeBetween(
                eq("BTC"), eq("USD"), eq(start.toDateTime()), eq(end.toDateTime())
        )).thenReturn(entities);

        // when
        var result = candleJpaAdapter.find(query);

        // then
        assertThat(result).hasSize(2);
        assertThat(result.get(0)).isInstanceOf(Candle.class);
        assertThat(result.get(0).symbol().value()).isEqualTo("BTC");
    }

    @Test
    @DisplayName("find - 올바른 파라미터로 repository 호출")
    void find_callsRepositoryWithCorrectParameters() {
        // given
        var symbol = Symbol.from("ETH");
        var currency = Currency.getInstance("KRW");
        var start = EpochMillis.from(1000L);
        var end = EpochMillis.from(5000L);
        var query = new CandlesQuery(symbol, currency, start, end);

        when(jpaRepository.findAllBySymbolAndCurrencyAndStartTimeBetween(
                eq("ETH"), eq("KRW"), eq(start.toDateTime()), eq(end.toDateTime())
        )).thenReturn(List.of());

        // when
        candleJpaAdapter.find(query);

        // then
        verify(jpaRepository).findAllBySymbolAndCurrencyAndStartTimeBetween(
                "ETH",
                "KRW",
                start.toDateTime(),
                end.toDateTime()
        );
    }

    @Test
    @DisplayName("find - 빈 결과 반환")
    void find_emptyResult_returnsEmptyList() {
        // given
        var query = new CandlesQuery(
                Symbol.from("BTC"),
                Currency.getInstance("USD"),
                EpochMillis.from(0L),
                EpochMillis.from(1000L)
        );
        when(jpaRepository.findAllBySymbolAndCurrencyAndStartTimeBetween(
                eq("BTC"), eq("USD"),
                eq(query.start().toDateTime()),
                eq(query.end().toDateTime())
        )).thenReturn(List.of());

        // when
        var result = candleJpaAdapter.find(query);

        // then
        assertThat(result).isEmpty();
    }

    private CandleEntity createEntity(String symbol, String currency, long epochMillis) {
        var startTime = LocalDateTime.ofInstant(
                java.time.Instant.ofEpochMilli(epochMillis),
                ZoneOffset.UTC
        );
        var candle = new Candle(
                Symbol.from(symbol),
                Currency.getInstance(currency),
                EpochMillis.from(epochMillis),
                new OHLCV(
                        Price.from("100"),
                        Price.from("150"),
                        Price.from("90"),
                        Price.from("120"),
                        Volume.from("1000"),
                        Price.from("5000")
                ),
                Timeframe.MINUTES
        );
        return new CandleEntity(candle);
    }
}
