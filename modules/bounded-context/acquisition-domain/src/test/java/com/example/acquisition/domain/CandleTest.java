package com.example.acquisition.domain;

import com.example.core.enums.Timeframe;
import com.example.core.exception.InvalidValueException;
import com.example.core.values.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CandleTest {

    private static final Symbol SYMBOL = Symbol.from("BTC");
    private static final Currency CURRENCY = Currency.getInstance("USD");
    private static final Timeframe TIMEFRAME = Timeframe.MINUTES;

    @Test
    @DisplayName("생성 - 유효한 값으로 생성")
    void constructor_validValues_createsCandle() {
        // given
        var startTime = EpochMillis.from(1000L);
        var ohlcv = createOHLCV("100", "150", "90", "120", "1000", "5000");

        // when
        var candle = new Candle(SYMBOL, CURRENCY, startTime, ohlcv, TIMEFRAME);

        // then
        assertThat(candle.symbol()).isEqualTo(SYMBOL);
        assertThat(candle.currency()).isEqualTo(CURRENCY);
        assertThat(candle.startTime()).isEqualTo(startTime);
        assertThat(candle.ohlcv()).isEqualTo(ohlcv);
        assertThat(candle.timeframe()).isEqualTo(TIMEFRAME);
    }

    @Test
    @DisplayName("생성 - symbol이 null이면 예외 발생")
    void constructor_nullSymbol_throwsException() {
        assertThatThrownBy(() -> new Candle(
                null,
                CURRENCY,
                EpochMillis.from(1000L),
                createOHLCV("100", "150", "90", "120", "1000", "5000"),
                TIMEFRAME
        ))
                .isInstanceOf(InvalidValueException.class)
                .hasMessage("Candle fields cannot be null");
    }

    @Test
    @DisplayName("생성 - currency가 null이면 예외 발생")
    void constructor_nullCurrency_throwsException() {
        assertThatThrownBy(() -> new Candle(
                SYMBOL,
                null,
                EpochMillis.from(1000L),
                createOHLCV("100", "150", "90", "120", "1000", "5000"),
                TIMEFRAME
        ))
                .isInstanceOf(InvalidValueException.class)
                .hasMessage("Candle fields cannot be null");
    }

    @Test
    @DisplayName("생성 - startTime이 null이면 예외 발생")
    void constructor_nullStartTime_throwsException() {
        assertThatThrownBy(() -> new Candle(
                SYMBOL,
                CURRENCY,
                null,
                createOHLCV("100", "150", "90", "120", "1000", "5000"),
                TIMEFRAME
        ))
                .isInstanceOf(InvalidValueException.class)
                .hasMessage("Candle fields cannot be null");
    }

    @Test
    @DisplayName("생성 - ohlcv가 null이면 예외 발생")
    void constructor_nullOhlcv_throwsException() {
        assertThatThrownBy(() -> new Candle(
                SYMBOL,
                CURRENCY,
                EpochMillis.from(1000L),
                null,
                TIMEFRAME
        ))
                .isInstanceOf(InvalidValueException.class)
                .hasMessage("Candle fields cannot be null");
    }

    @Test
    @DisplayName("생성 - timeframe이 null이면 예외 발생")
    void constructor_nullTimeframe_throwsException() {
        assertThatThrownBy(() -> new Candle(
                SYMBOL,
                CURRENCY,
                EpochMillis.from(1000L),
                createOHLCV("100", "150", "90", "120", "1000", "5000"),
                null
        ))
                .isInstanceOf(InvalidValueException.class)
                .hasMessage("Candle fields cannot be null");
    }

    @Test
    @DisplayName("merge - 두 캔들 병합 (this가 먼저인 경우)")
    void merge_thisBefore_mergesCorrectly() {
        // given
        var candle1 = new Candle(SYMBOL, CURRENCY, EpochMillis.from(1000L),
                createOHLCV("100", "150", "90", "120", "500", "2500"), TIMEFRAME);
        var candle2 = new Candle(SYMBOL, CURRENCY, EpochMillis.from(2000L),
                createOHLCV("120", "180", "110", "150", "300", "1500"), TIMEFRAME);

        // when
        var merged = candle1.merge(candle2);

        // then
        assertThat(merged.startTime()).isEqualTo(EpochMillis.from(1000L)); // 먼저인 것의 시작시간
        assertThat(merged.ohlcv().open().value()).isEqualByComparingTo("100"); // 먼저인 것의 open
        assertThat(merged.ohlcv().high().value()).isEqualByComparingTo("180"); // 둘 중 최대
        assertThat(merged.ohlcv().low().value()).isEqualByComparingTo("90"); // 둘 중 최소
        assertThat(merged.ohlcv().close().value()).isEqualByComparingTo("150"); // 나중인 것의 close
        assertThat(merged.ohlcv().volume().value()).isEqualByComparingTo("800"); // 합산
        assertThat(merged.ohlcv().turnover().value()).isEqualByComparingTo("4000"); // 합산
    }

    @Test
    @DisplayName("merge - 두 캔들 병합 (this가 나중인 경우)")
    void merge_thisAfter_mergesCorrectly() {
        // given
        var candle1 = new Candle(SYMBOL, CURRENCY, EpochMillis.from(2000L),
                createOHLCV("120", "180", "110", "150", "300", "1500"), TIMEFRAME);
        var candle2 = new Candle(SYMBOL, CURRENCY, EpochMillis.from(1000L),
                createOHLCV("100", "150", "90", "120", "500", "2500"), TIMEFRAME);

        // when
        var merged = candle1.merge(candle2);

        // then
        assertThat(merged.startTime()).isEqualTo(EpochMillis.from(1000L)); // 먼저인 것의 시작시간
        assertThat(merged.ohlcv().open().value()).isEqualByComparingTo("100"); // 먼저인 것의 open
        assertThat(merged.ohlcv().high().value()).isEqualByComparingTo("180"); // 둘 중 최대
        assertThat(merged.ohlcv().low().value()).isEqualByComparingTo("90"); // 둘 중 최소
        assertThat(merged.ohlcv().close().value()).isEqualByComparingTo("150"); // 나중인 것의 close
    }

    @Test
    @DisplayName("merge - high가 같은 경우")
    void merge_sameHigh_usesFirstHigh() {
        // given
        var candle1 = new Candle(SYMBOL, CURRENCY, EpochMillis.from(1000L),
                createOHLCV("100", "150", "90", "120", "500", "2500"), TIMEFRAME);
        var candle2 = new Candle(SYMBOL, CURRENCY, EpochMillis.from(2000L),
                createOHLCV("120", "150", "100", "140", "300", "1500"), TIMEFRAME);

        // when
        var merged = candle1.merge(candle2);

        // then
        assertThat(merged.ohlcv().high().value()).isEqualByComparingTo("150");
    }

    @Test
    @DisplayName("merge - low가 같은 경우")
    void merge_sameLow_usesFirstLow() {
        // given
        var candle1 = new Candle(SYMBOL, CURRENCY, EpochMillis.from(1000L),
                createOHLCV("100", "150", "90", "120", "500", "2500"), TIMEFRAME);
        var candle2 = new Candle(SYMBOL, CURRENCY, EpochMillis.from(2000L),
                createOHLCV("120", "180", "90", "140", "300", "1500"), TIMEFRAME);

        // when
        var merged = candle1.merge(candle2);

        // then
        assertThat(merged.ohlcv().low().value()).isEqualByComparingTo("90");
    }

    @Test
    @DisplayName("merge - before의 high가 after의 high보다 큰 경우")
    void merge_beforeHighGreaterThanAfterHigh_usesBeforeHigh() {
        // given - 먼저인 캔들(candle1)의 high(200)가 나중인 캔들(candle2)의 high(150)보다 큼
        var candle1 = new Candle(SYMBOL, CURRENCY, EpochMillis.from(1000L),
                createOHLCV("100", "200", "90", "120", "500", "2500"), TIMEFRAME);
        var candle2 = new Candle(SYMBOL, CURRENCY, EpochMillis.from(2000L),
                createOHLCV("120", "150", "100", "140", "300", "1500"), TIMEFRAME);

        // when
        var merged = candle1.merge(candle2);

        // then
        assertThat(merged.ohlcv().high().value()).isEqualByComparingTo("200");
    }

    @Test
    @DisplayName("merge - after의 low가 before의 low보다 작은 경우")
    void merge_afterLowLessThanBeforeLow_usesAfterLow() {
        // given - 나중인 캔들(candle2)의 low(80)가 먼저인 캔들(candle1)의 low(90)보다 작음
        var candle1 = new Candle(SYMBOL, CURRENCY, EpochMillis.from(1000L),
                createOHLCV("100", "150", "90", "120", "500", "2500"), TIMEFRAME);
        var candle2 = new Candle(SYMBOL, CURRENCY, EpochMillis.from(2000L),
                createOHLCV("120", "180", "80", "140", "300", "1500"), TIMEFRAME);

        // when
        var merged = candle1.merge(candle2);

        // then
        assertThat(merged.ohlcv().low().value()).isEqualByComparingTo("80");
    }

    @Test
    @DisplayName("compareTo - 더 이른 시간이면 음수 반환")
    void compareTo_earlier_returnsNegative() {
        // given
        var candle1 = new Candle(SYMBOL, CURRENCY, EpochMillis.from(1000L),
                createOHLCV("100", "150", "90", "120", "500", "2500"), TIMEFRAME);
        var candle2 = new Candle(SYMBOL, CURRENCY, EpochMillis.from(2000L),
                createOHLCV("120", "180", "110", "150", "300", "1500"), TIMEFRAME);

        // then
        assertThat(candle1.compareTo(candle2)).isLessThan(0);
    }

    @Test
    @DisplayName("compareTo - 더 늦은 시간이면 양수 반환")
    void compareTo_later_returnsPositive() {
        // given
        var candle1 = new Candle(SYMBOL, CURRENCY, EpochMillis.from(2000L),
                createOHLCV("120", "180", "110", "150", "300", "1500"), TIMEFRAME);
        var candle2 = new Candle(SYMBOL, CURRENCY, EpochMillis.from(1000L),
                createOHLCV("100", "150", "90", "120", "500", "2500"), TIMEFRAME);

        // then
        assertThat(candle1.compareTo(candle2)).isGreaterThan(0);
    }

    @Test
    @DisplayName("compareTo - 같은 시간이면 0 반환")
    void compareTo_sameTime_returnsZero() {
        // given
        var candle1 = new Candle(SYMBOL, CURRENCY, EpochMillis.from(1000L),
                createOHLCV("100", "150", "90", "120", "500", "2500"), TIMEFRAME);
        var candle2 = new Candle(SYMBOL, CURRENCY, EpochMillis.from(1000L),
                createOHLCV("120", "180", "110", "150", "300", "1500"), TIMEFRAME);

        // then
        assertThat(candle1.compareTo(candle2)).isZero();
    }

    private OHLCV createOHLCV(String open, String high, String low, String close, String volume, String turnover) {
        return new OHLCV(
                Price.from(open),
                Price.from(high),
                Price.from(low),
                Price.from(close),
                Volume.from(volume),
                Price.from(turnover)
        );
    }
}
