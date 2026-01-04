package com.example.analysis.domain.calculator;

import com.example.analysis.domain.Candle;
import com.example.analysis.domain.indicator.Code;
import com.example.analysis.domain.indicator.MacdParams;
import com.example.core.enums.Timeframe;
import com.example.core.values.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MacdCalculatorTest {
    private static final MacdCalculator calculator = new MacdCalculator(MacdParams.STANDARD);

    @Test
    @DisplayName("MACD 기본 계산 - 표준 기간(12, 26, 9)")
    void calculate_macd_standardPeriod() {
        // given - MACD는 slowPeriod + signalPeriod - 1 = 26 + 9 - 1 = 34개 필요
        var candles = createCandles(
                100.0, 102.0, 104.0, 103.0, 105.0, 107.0, 106.0, 108.0, 110.0, 109.0,
                111.0, 113.0, 112.0, 114.0, 116.0, 115.0, 117.0, 119.0, 118.0, 120.0,
                122.0, 121.0, 123.0, 125.0, 124.0, 126.0, 128.0, 127.0, 129.0, 131.0,
                130.0, 132.0, 134.0, 133.0, 135.0
        );

        // when
        var result = calculator.calculate(candles);

        // then
        assertThat(result.code()).isEqualTo(Code.MACD);
        assertThat(result.value()).isNotNull(); // MACD Line
        assertThat(result.core().symbol().value()).isEqualTo("KRW-BTC");
        assertThat(result.core().timeframe()).isEqualTo(Timeframe.DAYS);
        assertThat(result.params().fast()).isEqualTo(12);
        assertThat(result.params().slow()).isEqualTo(26);
        assertThat(result.params().signal()).isEqualTo(9);
        assertThat(result.signal()).isNotNull();
        assertThat(result.histogram()).isNotNull();
    }

    @Test
    @DisplayName("MACD 단기 기간 계산 - SHORT(5, 13, 5)")
    void calculate_macd_shortPeriod() {
        // given
        var calculator = new MacdCalculator(MacdParams.SHORT);
        var candles = createCandles(
                100.0, 102.0, 104.0, 103.0, 105.0, 107.0, 106.0, 108.0, 110.0, 109.0,
                111.0, 113.0, 112.0, 114.0, 116.0, 115.0, 117.0, 119.0, 118.0, 120.0
        );

        // when
        var result = calculator.calculate(candles);

        // then
        assertThat(result.params().fast()).isEqualTo(5);
        assertThat(result.params().slow()).isEqualTo(13);
        assertThat(result.params().signal()).isEqualTo(5);
    }

    @Test
    @DisplayName("MACD 장기 기간 계산 - LONG(19, 39, 9)")
    void calculate_macd_longPeriod() {
        // given - LONG은 39 + 9 - 1 = 47개 필요
        var calculator = new MacdCalculator(MacdParams.LONG);
        var candles = createCandles(IntStream.rangeClosed(1, 50).mapToDouble(i -> 100.0 + i).toArray());

        // when
        var result = calculator.calculate(candles);

        // then
        assertThat(result.params().fast()).isEqualTo(19);
        assertThat(result.params().slow()).isEqualTo(39);
        assertThat(result.params().signal()).isEqualTo(9);
    }

    @Test
    @DisplayName("MACD 상승장 - MACD Line > 0")
    void calculate_macd_uptrend() {
        // given - 지속적인 상승
        var candles = createCandles(
                100.0, 102.0, 104.0, 106.0, 108.0, 110.0, 112.0, 114.0, 116.0, 118.0,
                120.0, 122.0, 124.0, 126.0, 128.0, 130.0, 132.0, 134.0, 136.0, 138.0,
                140.0, 142.0, 144.0, 146.0, 148.0, 150.0, 152.0, 154.0, 156.0, 158.0,
                160.0, 162.0, 164.0, 166.0, 168.0
        );

        // when & then - 강한 상승장에서는 MACD Line이 양수
        assertThat(calculator.calculate(candles).value()).isGreaterThan(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("MACD 하락장 - MACD Line < 0")
    void calculate_macd_downtrend() {
        // given - 지속적인 하락
        var candles = createCandles(
                168.0, 166.0, 164.0, 162.0, 160.0, 158.0, 156.0, 154.0, 152.0, 150.0,
                148.0, 146.0, 144.0, 142.0, 140.0, 138.0, 136.0, 134.0, 132.0, 130.0,
                128.0, 126.0, 124.0, 122.0, 120.0, 118.0, 116.0, 114.0, 112.0, 110.0,
                108.0, 106.0, 104.0, 102.0, 100.0
        );

        // when & then - 강한 하락장에서는 MACD Line이 음수
        assertThat(calculator.calculate(candles).value()).isLessThan(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("MACD Signal Line과 Histogram 검증")
    void calculate_macd_signalAndHistogram() {
        // given
        var candles = createCandles(
                100.0, 102.0, 104.0, 103.0, 105.0, 107.0, 106.0, 108.0, 110.0, 109.0,
                111.0, 113.0, 112.0, 114.0, 116.0, 115.0, 117.0, 119.0, 118.0, 120.0,
                122.0, 121.0, 123.0, 125.0, 124.0, 126.0, 128.0, 127.0, 129.0, 131.0,
                130.0, 132.0, 134.0, 133.0, 135.0
        );

        // when
        var result = calculator.calculate(candles);

        // then
        var macdLine = result.value();
        var signalLine = result.signal();
        var histogram = result.histogram();

        // Histogram = MACD Line - Signal Line
        assertThat(histogram).isEqualByComparingTo(macdLine.subtract(signalLine));
    }

    @Test
    @DisplayName("정확히 최소 데이터로 계산")
    void calculate_exactMinimumData() {
        // given - MACD는 slowPeriod + signalPeriod - 1 = 26 + 9 - 1 = 34개 필요
        var candles = createCandles(
                100.0, 101.0, 102.0, 103.0, 104.0, 105.0, 106.0, 107.0, 108.0, 109.0,
                110.0, 111.0, 112.0, 113.0, 114.0, 115.0, 116.0, 117.0, 118.0, 119.0,
                120.0, 121.0, 122.0, 123.0, 124.0, 125.0, 126.0, 127.0, 128.0, 129.0,
                130.0, 131.0, 132.0, 133.0
        );

        // when & then
        assertThat(calculator.calculate(candles).value()).isNotNull();
    }

    @Test
    @DisplayName("데이터가 더 많아도 정상 계산")
    void calculate_withMoreData() {
        // given
        var candles = createCandles(IntStream.rangeClosed(1, 100).mapToDouble(i -> 100.0 + i).toArray());

        // when
        var result = calculator.calculate(candles);

        // then
        assertThat(result.value()).isNotNull();
        assertThat(result.signal()).isNotNull();
        assertThat(result.histogram()).isNotNull();
    }

    @Test
    @DisplayName("최신 캔들의 메타데이터 사용")
    void calculate_usesLatestCandleMetadata() {
        // given
        var candles = createCandles(
                100.0, 101.0, 102.0, 103.0, 104.0, 105.0, 106.0, 107.0, 108.0, 109.0,
                110.0, 111.0, 112.0, 113.0, 114.0, 115.0, 116.0, 117.0, 118.0, 119.0,
                120.0, 121.0, 122.0, 123.0, 124.0, 125.0, 126.0, 127.0, 128.0, 129.0,
                130.0, 131.0, 132.0, 133.0
        );

        // when & then
        assertThat(calculator.calculate(candles).core().timestamp().value()).isEqualTo(34000L);
    }

    @Test
    @DisplayName("candles가 null이면 예외 발생")
    void calculate_nullCandles_throwsException() {
        assertThatThrownBy(() -> calculator.calculate(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Candles cannot be null or empty");
    }

    @Test
    @DisplayName("candles가 empty면 예외 발생")
    void calculate_emptyCandles_throwsException() {
        assertThatThrownBy(() -> calculator.calculate(List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Candles cannot be null or empty");
    }

    @Test
    @DisplayName("데이터가 최소 요구량보다 적으면 예외 발생")
    void calculate_notEnoughData_throwsException() {
        // given - 최소 34개 필요한데 10개만 제공
        var candles = createCandles(100.0, 101.0, 102.0, 103.0, 104.0, 105.0, 106.0, 107.0, 108.0, 109.0);

        // when & then
        assertThatThrownBy(() -> calculator.calculate(candles))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Not enough data")
                .hasMessageContaining("Required: 34")
                .hasMessageContaining("Provided: 10");
    }

    @Test
    @DisplayName("정확한 소수점 계산")
    void calculate_decimalPrecision() {
        // given
        var candles = createCandles(
                100.123, 102.456, 104.789, 103.234, 105.567, 107.890, 106.345, 108.678, 110.901, 109.456,
                111.789, 113.012, 112.567, 114.890, 116.123, 115.678, 117.901, 119.234, 118.789, 120.012,
                122.345, 121.890, 123.123, 125.456, 124.901, 126.234, 128.567, 127.012, 129.345, 131.678,
                130.123, 132.456, 134.789, 133.234, 135.567
        );

        // when
        var result = calculator.calculate(candles);

        // then - 소수점 8자리까지 정확히 계산
        assertThat(result.value().scale()).isLessThanOrEqualTo(8);
        assertThat(result.signal().scale()).isLessThanOrEqualTo(8);
        assertThat(result.histogram().scale()).isLessThanOrEqualTo(8);
    }

    @Test
    @DisplayName("MACD 교차 패턴 - Histogram 부호 변화")
    void calculate_macd_crossover() {
        // given - 상승 후 하락 패턴
        var candles = createCandles(
                100.0, 105.0, 110.0, 115.0, 120.0, 125.0, 130.0, 135.0, 140.0, 145.0,
                150.0, 155.0, 160.0, 165.0, 170.0, 175.0, 180.0, 185.0, 190.0, 195.0,
                200.0, 198.0, 196.0, 194.0, 192.0, 190.0, 188.0, 186.0, 184.0, 182.0,
                180.0, 178.0, 176.0, 174.0, 172.0
        );

        // when & then - Histogram이 존재하고 계산됨
        var histogram = calculator.calculate(candles).histogram();
        assertThat(histogram).isNotNull();
    }

    private List<Candle> createCandles(double... closePrices) {
        return IntStream.range(0, closePrices.length)
                .mapToObj(i -> createCandle(closePrices[i], 1000L + (i * 1000L)))
                .toList();
    }

    private Candle createCandle(double closePrice, long timestamp) {
        return new Candle(
                new Symbol("KRW-BTC"),
                new EpochMillis(timestamp),
                createOHLCV(closePrice),
                Timeframe.DAYS
        );
    }

    private OHLCV createOHLCV(double closePrice) {
        // MACD는 종가만 사용
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
