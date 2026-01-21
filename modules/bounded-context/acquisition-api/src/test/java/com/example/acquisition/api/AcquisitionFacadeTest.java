package com.example.acquisition.api;

import com.example.acquisition.application.GetCandles;
import com.example.acquisition.application.GetCandlesRequest;
import com.example.acquisition.domain.Candle;
import com.example.contract.acquisition.AcquisitionCandlesRequest;
import com.example.core.enums.Timeframe;
import com.example.core.values.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Currency;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AcquisitionFacadeTest {

    @Mock
    private GetCandles getCandles;

    private AcquisitionFacade acquisitionFacade;

    @BeforeEach
    void setUp() {
        acquisitionFacade = new AcquisitionFacade(getCandles);
    }

    @Test
    @DisplayName("findAcquisitionCandles - 캔들 조회 후 response로 변환")
    void findAcquisitionCandles_returnsConvertedResponses() {
        // given
        var symbol = Symbol.from("BTC");
        var currency = Currency.getInstance("USD");
        var start = EpochMillis.from(1000L);
        var end = EpochMillis.from(2000L);
        var request = new AcquisitionCandlesRequest(symbol, currency, start, end);

        var candles = List.of(
                createCandle(symbol, currency, 1000L),
                createCandle(symbol, currency, 2000L)
        );
        when(getCandles.execute(any())).thenReturn(candles);

        // when
        var result = acquisitionFacade.findAcquisitionCandles(request);

        // then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).symbol()).isEqualTo(symbol);
        assertThat(result.get(0).currency()).isEqualTo(currency);
        assertThat(result.get(0).startTime()).isEqualTo(EpochMillis.from(1000L));
        assertThat(result.get(0).timeframe()).isEqualTo(Timeframe.HOURS);
    }

    @Test
    @DisplayName("findAcquisitionCandles - GetCandles에 올바른 request 전달")
    void findAcquisitionCandles_passesCorrectRequestToGetCandles() {
        // given
        var symbol = Symbol.from("ETH");
        var currency = Currency.getInstance("KRW");
        var start = EpochMillis.from(5000L);
        var end = EpochMillis.from(10000L);
        var request = new AcquisitionCandlesRequest(symbol, currency, start, end);

        when(getCandles.execute(any())).thenReturn(List.of());

        // when
        acquisitionFacade.findAcquisitionCandles(request);

        // then
        ArgumentCaptor<GetCandlesRequest> captor = ArgumentCaptor.forClass(GetCandlesRequest.class);
        verify(getCandles).execute(captor.capture());

        var capturedRequest = captor.getValue();
        assertThat(capturedRequest.symbol()).isEqualTo(symbol);
        assertThat(capturedRequest.currency()).isEqualTo(currency);
        assertThat(capturedRequest.start()).isEqualTo(start);
        assertThat(capturedRequest.end()).isEqualTo(end);
    }

    @Test
    @DisplayName("findAcquisitionCandles - 빈 결과 반환")
    void findAcquisitionCandles_emptyResult_returnsEmptyList() {
        // given
        var request = new AcquisitionCandlesRequest(
                Symbol.from("BTC"),
                Currency.getInstance("USD"),
                EpochMillis.from(1000L),
                EpochMillis.from(2000L)
        );
        when(getCandles.execute(any())).thenReturn(List.of());

        // when
        var result = acquisitionFacade.findAcquisitionCandles(request);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("findAcquisitionCandles - OHLCV 데이터 올바르게 변환")
    void findAcquisitionCandles_convertsOhlcvCorrectly() {
        // given
        var symbol = Symbol.from("BTC");
        var currency = Currency.getInstance("USD");
        var ohlcv = new OHLCV(
                Price.from("100"),
                Price.from("150"),
                Price.from("90"),
                Price.from("120"),
                Volume.from("1000"),
                Price.from("5000")
        );
        var candle = new Candle(symbol, currency, EpochMillis.from(1000L), ohlcv, Timeframe.HOURS);

        var request = new AcquisitionCandlesRequest(
                symbol,
                currency,
                EpochMillis.from(1000L),
                EpochMillis.from(2000L)
        );
        when(getCandles.execute(any())).thenReturn(List.of(candle));

        // when
        var result = acquisitionFacade.findAcquisitionCandles(request);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).ohlcv()).isEqualTo(ohlcv);
    }

    private Candle createCandle(Symbol symbol, Currency currency, long epochMillis) {
        var ohlcv = new OHLCV(
                Price.from("100"),
                Price.from("150"),
                Price.from("90"),
                Price.from("120"),
                Volume.from("1000"),
                Price.from("5000")
        );
        return new Candle(symbol, currency, EpochMillis.from(epochMillis), ohlcv, Timeframe.HOURS);
    }
}
