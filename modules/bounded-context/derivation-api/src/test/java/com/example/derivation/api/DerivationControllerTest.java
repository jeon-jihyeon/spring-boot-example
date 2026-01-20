package com.example.derivation.api;

import com.example.core.enums.Timeframe;
import com.example.core.values.EpochMillis;
import com.example.core.values.Symbol;
import com.example.derivation.application.GetIndicators;
import com.example.derivation.domain.Indicator;
import com.example.derivation.domain.indicator.Code;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Currency;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DerivationControllerTest {

    @Mock
    private GetIndicators getIndicators;

    @InjectMocks
    private DerivationController controller;

    @Test
    @DisplayName("getIndicators - 요청을 처리하고 응답 반환")
    void getIndicators_returnsResponse() {
        // given
        var request = new IndicatorRequest("KRW-BTC", "KRW", 1000L, 2000L);
        when(getIndicators.execute(argThat(req -> req.symbol().value().equals("KRW-BTC"))))
                .thenReturn(List.of(
                        new Indicator(
                                new Symbol("KRW-BTC"),
                                Timeframe.DAYS,
                                new EpochMillis(1000L),
                                Code.EMA,
                                Map.of("value", "150.0", "period", "12")
                        ),
                        new Indicator(
                                new Symbol("KRW-BTC"),
                                Timeframe.DAYS,
                                new EpochMillis(1000L),
                                Code.MACD,
                                Map.of("value", "5.0", "signal", "4.0", "histogram", "1.0")
                        )
                ));

        // when
        var result = controller.getIndicators(request);

        // then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).code()).isEqualTo("EMA");
        assertThat(result.get(1).code()).isEqualTo("MACD");
    }

    @Test
    @DisplayName("getIndicators - 올바른 CandlesRequest로 변환")
    void getIndicators_convertsToCandlesRequest() {
        // given
        var request = new IndicatorRequest("KRW-ETH", "USD", 5000L, 10000L);
        when(getIndicators.execute(argThat(req -> req.symbol().value().equals("KRW-ETH"))))
                .thenReturn(List.of());

        // when
        controller.getIndicators(request);

        // then
        verify(getIndicators).execute(argThat(req -> {
            assertThat(req.symbol().value()).isEqualTo("KRW-ETH");
            assertThat(req.currency()).isEqualTo(Currency.getInstance("USD"));
            assertThat(req.start().value()).isEqualTo(5000L);
            assertThat(req.end().value()).isEqualTo(10000L);
            return true;
        }));
    }
}
