package com.example.acquisition.infra;

import com.example.acquisition.domain.Candle;
import com.example.core.enums.Timeframe;
import com.example.core.values.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;

class CandleEntityTest {

    @Test
    @DisplayName("Candle 모델로 생성 후 toModel로 복원")
    void constructorAndToModel_roundTrip_preservesData() {
        // given
        var symbol = Symbol.from("BTC");
        var currency = Currency.getInstance("USD");
        var startTime = EpochMillis.from(3600000L); // 1시간 후
        var ohlcv = new OHLCV(
                Price.from("100.12345678"),
                Price.from("150.87654321"),
                Price.from("90.11111111"),
                Price.from("120.22222222"),
                Volume.from("1000.33333333"),
                Price.from("50000.44444444")
        );
        var candle = new Candle(symbol, currency, startTime, ohlcv, Timeframe.MINUTES);

        // when
        var entity = new CandleEntity(candle);
        var restored = entity.toModel();

        // then
        assertThat(restored.symbol().value()).isEqualTo(symbol.value());
        assertThat(restored.currency()).isEqualTo(currency);
        assertThat(restored.startTime().value()).isEqualTo(startTime.value());
        assertThat(restored.ohlcv().open().value()).isEqualByComparingTo(ohlcv.open().value());
        assertThat(restored.ohlcv().high().value()).isEqualByComparingTo(ohlcv.high().value());
        assertThat(restored.ohlcv().low().value()).isEqualByComparingTo(ohlcv.low().value());
        assertThat(restored.ohlcv().close().value()).isEqualByComparingTo(ohlcv.close().value());
        assertThat(restored.ohlcv().volume().value()).isEqualByComparingTo(ohlcv.volume().value());
        assertThat(restored.ohlcv().turnover().value()).isEqualByComparingTo(ohlcv.turnover().value());
        assertThat(restored.timeframe()).isEqualTo(Timeframe.MINUTES); // 항상 MINUTES로 저장
    }

    @Test
    @DisplayName("기본 생성자로 생성")
    void defaultConstructor_createsEntity() {
        // when
        var entity = new CandleEntity();

        // then
        assertThat(entity).isNotNull();
    }

    @Test
    @DisplayName("toModel - Timeframe은 항상 MINUTES")
    void toModel_timeframeIsAlwaysMinutes() {
        // given
        var candle = createCandle(Timeframe.HOURS); // HOURS로 생성해도
        var entity = new CandleEntity(candle);

        // when
        var restored = entity.toModel();

        // then
        assertThat(restored.timeframe()).isEqualTo(Timeframe.MINUTES); // MINUTES로 복원됨
    }

    private Candle createCandle(Timeframe timeframe) {
        return new Candle(
                Symbol.from("ETH"),
                Currency.getInstance("KRW"),
                EpochMillis.from(1000L),
                new OHLCV(
                        Price.from("100"),
                        Price.from("150"),
                        Price.from("90"),
                        Price.from("120"),
                        Volume.from("1000"),
                        Price.from("5000")
                ),
                timeframe
        );
    }
}
