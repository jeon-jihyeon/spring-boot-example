package com.example.derivation.infra;

import com.example.contract.acquisition.AcquisitionCandleResponse;
import com.example.contract.acquisition.AcquisitionContract;
import com.example.core.enums.Timeframe;
import com.example.core.values.*;
import com.example.derivation.application.CandlesRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AcquisitionCandleFetcherTest {

    @Mock
    private AcquisitionContract acquisitionContract;

    @InjectMocks
    private AcquisitionCandleFetcher fetcher;

    @Test
    @DisplayName("find - AcquisitionContract를 호출하고 Candle로 변환")
    void find_callsContractAndConvertsToCandle() {
        // given
        var request = new CandlesRequest(
                new Symbol("KRW-BTC"),
                Currency.getInstance("KRW"),
                new EpochMillis(1000L),
                new EpochMillis(2000L)
        );
        when(acquisitionContract
                .findAcquisitionCandles(argThat(req -> req.symbol().value().equals("KRW-BTC"))))
                .thenReturn(List.of(new AcquisitionCandleResponse(
                        new Symbol("KRW-BTC"),
                        Currency.getInstance("KRW"),
                        new EpochMillis(1000L),
                        createOHLCV(),
                        Timeframe.DAYS
                )));

        // when
        var result = fetcher.find(request);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).symbol().value()).isEqualTo("KRW-BTC");
        assertThat(result.get(0).startTime().value()).isEqualTo(1000L);
        assertThat(result.get(0).timeframe()).isEqualTo(Timeframe.DAYS);
    }

    @Test
    @DisplayName("find - 올바른 AcquisitionCandlesRequest로 변환")
    void find_convertsToAcquisitionRequest() {
        // given
        var request = new CandlesRequest(
                new Symbol("KRW-ETH"),
                Currency.getInstance("USD"),
                new EpochMillis(5000L),
                new EpochMillis(10000L)
        );
        when(acquisitionContract
                .findAcquisitionCandles(argThat(req -> req.symbol().value().equals("KRW-ETH"))))
                .thenReturn(List.of());

        // when
        fetcher.find(request);

        // then
        verify(acquisitionContract).findAcquisitionCandles(argThat(req -> {
            assertThat(req.symbol().value()).isEqualTo("KRW-ETH");
            assertThat(req.currency()).isEqualTo(Currency.getInstance("USD"));
            assertThat(req.start().value()).isEqualTo(5000L);
            assertThat(req.end().value()).isEqualTo(10000L);
            return true;
        }));
    }

    @Test
    @DisplayName("find - 빈 응답 처리")
    void find_handlesEmptyResponse() {
        // given
        var request = new CandlesRequest(
                new Symbol("KRW-BTC"),
                Currency.getInstance("KRW"),
                new EpochMillis(1000L),
                new EpochMillis(2000L)
        );
        when(acquisitionContract
                .findAcquisitionCandles(argThat(req -> req.symbol().value().equals("KRW-BTC"))))
                .thenReturn(List.of());

        // when
        var result = fetcher.find(request);

        // then
        assertThat(result).isEmpty();
    }

    private OHLCV createOHLCV() {
        return new OHLCV(
                new Price(BigDecimal.valueOf(100.0 - 5)),
                new Price(BigDecimal.valueOf(100.0 + 5)),
                new Price(BigDecimal.valueOf(100.0 - 10)),
                new Price(BigDecimal.valueOf(100.0)),
                new Volume(BigDecimal.valueOf(1000)),
                Price.from(5000)
        );
    }
}
