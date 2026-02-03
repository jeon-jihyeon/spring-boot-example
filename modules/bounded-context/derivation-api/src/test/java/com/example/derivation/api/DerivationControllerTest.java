package com.example.derivation.api;

import com.example.core.enums.Timeframe;
import com.example.core.values.EpochMillis;
import com.example.core.values.Symbol;
import com.example.derivation.application.GetStandardMacd;
import com.example.derivation.domain.Indicator;
import com.example.derivation.domain.indicator.Code;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Currency;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DerivationControllerTest {

    @Mock
    private GetStandardMacd getStandardMacd;

    @InjectMocks
    private DerivationController controller;

    @Test
    @DisplayName("getMacd - 표준 MACD 응답 반환")
    void getMacd_returnsResponse() {
        // given
        var request = new IndicatorRequest("KRW-BTC", "KRW", 1000L, 2000L);
        when(getStandardMacd.execute(argThat(req -> req.symbol().value().equals("KRW-BTC"))))
                .thenReturn(new Indicator(
                        new Symbol("KRW-BTC"),
                        Timeframe.DAYS,
                        new EpochMillis(1000L),
                        Code.MACD,
                        Map.of("value", "5.0", "signal", "4.0", "histogram", "1.0")
                ));

        // when
        var result = controller.getMacd(request);

        // then
        assertThat(result.code()).isEqualTo("MACD");
    }

    @Test
    @DisplayName("getMacd - 올바른 IndicatorParam으로 변환")
    void getMacd_convertsToIndicatorParam() {
        // given
        var request = new IndicatorRequest("KRW-ETH", "USD", 5000L, 10000L);
        when(getStandardMacd.execute(argThat(req -> req.symbol().value().equals("KRW-ETH"))))
                .thenReturn(new Indicator(
                        new Symbol("KRW-ETH"),
                        Timeframe.DAYS,
                        new EpochMillis(5000L),
                        Code.MACD,
                        Map.of("value", "0.0", "signal", "0.0", "histogram", "0.0")
                ));

        // when
        controller.getMacd(request);

        // then
        verify(getStandardMacd).execute(argThat(req -> {
            assertThat(req.symbol().value()).isEqualTo("KRW-ETH");
            assertThat(req.currency()).isEqualTo(Currency.getInstance("USD"));
            assertThat(req.start().value()).isEqualTo(5000L);
            assertThat(req.end().value()).isEqualTo(10000L);
            return true;
        }));
    }
}
