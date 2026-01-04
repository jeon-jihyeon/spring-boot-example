package com.example.analysis.domain.calculator;

import com.example.analysis.domain.Candle;
import com.example.analysis.domain.indicator.Code;
import com.example.analysis.domain.indicator.EmaParams;
import com.example.core.enums.Timeframe;
import com.example.core.values.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmaCalculatorTest {

    private final EmaCalculator calculator = new EmaCalculator(EmaParams.SHORT);

    @Test
    @DisplayName("EMA(9) 정상 계산 - 지수 이동 평균이 정확히 계산")
    void calculate_ema9_success() {
        // given
        // 종가: 100 ~ 180 (9개)
        // period = 9이므로 EMA 초기값 = SMA(9)
        var candles = createCandles(100.0, 110.0, 120.0, 130.0, 140.0, 150.0, 160.0, 170.0, 180.0);

        // when
        var result = calculator.calculate(candles);

        // then
        assertThat(result.code()).isEqualTo(Code.EMA);
        assertThat(result.value()).isEqualByComparingTo("140.00000000");
        assertThat(result.core().symbol().value()).isEqualTo("KRW-BTC");
        assertThat(result.core().timeframe()).isEqualTo(Timeframe.DAYS);
        assertThat(result.period()).isEqualTo(9);
    }

    @Test
    @DisplayName("EMA(9) 계산 - 추가 데이터 반영")
    void calculate_ema9_withMoreData() {
        // given
        // 종가: 100 ~ 190 (10개)
        // 승수 = 2 / (9 + 1) = 0.2
        // 초기 SMA(9) = 140, EMA[10] = (190 × 0.2) + (140 × 0.8) = 150
        var candles = createCandles(
                100.0, 110.0, 120.0, 130.0, 140.0,
                150.0, 160.0, 170.0, 180.0, 190.0
        );

        // when & then
        assertThat(calculator.calculate(candles).value()).isEqualByComparingTo("150.00000000");
    }

    @Test
    @DisplayName("EMA는 최근 데이터에 더 민감")
    void ema_moreResponsive_thanSma() {
        // given
        // 급격한 가격 상승 시나리오
        var candles = createCandles(
                100.0, 100.0, 100.0, 100.0, 100.0,
                100.0, 100.0, 100.0, 100.0, 200.0
        );

        // when
        var ema = calculator.calculate(candles);
        var sma = candles.subList(candles.size() - 9, candles.size()).stream()
                .map(candle -> candle.ohlcv().close().value())
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(9), 8, RoundingMode.HALF_UP);

        // then
        // EMA는 최근 급등에 더 빨리 반응
        assertThat(ema.value()).isGreaterThan(sma);
    }

    @Test
    @DisplayName("EMA(9) 평탄한 가격이면 동일")
    void calculate_ema9_flatPrice() {
        // given
        var candles = createCandles(
                120.0, 120.0, 120.0, 120.0, 120.0,
                120.0, 120.0, 120.0, 120.0
        );

        // when & then
        assertThat(calculator.calculate(candles).value()).isEqualByComparingTo("120.00000000");
    }

    @Test
    @DisplayName("정확히 period 개수만큼의 데이터로 계산")
    void calculate_exactPeriodData() {
        // given
        var candles = createCandles(100.0, 200.0, 300.0, 400.0, 500.0, 600.0, 700.0, 800.0, 900.0);

        // when & then
        // 데이터가 period와 같으면 초기 SMA와 동일
        // SMA = (100 + 200 + ... + 900) / 9 = 500
        assertThat(calculator.calculate(candles).value()).isEqualByComparingTo("500.00000000");
    }

    @Test
    @DisplayName("정확한 소수점 계산")
    void calculate_decimalPrecision() {
        // given
        var candles = createCandles(
                100.123, 100.123, 100.123, 100.123, 100.123,
                100.123, 100.123, 100.123, 100.123
        );

        // when & then
        assertThat(calculator.calculate(candles).value()).isEqualByComparingTo("100.12300000");
    }

    @Test
    @DisplayName("최신 캔들의 메타데이터 사용")
    void calculate_usesLatestCandleMetadata() {
        // given
        var candles = List.of(
                createCandle(100.0, 1000L),
                createCandle(200.0, 2000L),
                createCandle(300.0, 3000L),
                createCandle(400.0, 4000L),
                createCandle(500.0, 5000L),
                createCandle(600.0, 6000L),
                createCandle(700.0, 7000L),
                createCandle(800.0, 8000L),
                createCandle(900.0, 9000L)
        );

        // when & then
        assertThat(calculator.calculate(candles).core().timestamp().value()).isEqualTo(9000L);
    }

    @Test
    @DisplayName("candles가 null이면 예외 발생")
    void calculate_nullCandles_throwsException() {
        // when & then
        var calculator = new EmaCalculator(EmaParams.SHORT);
        assertThatThrownBy(() -> calculator.calculate(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Candles cannot be null or empty");
    }

    @Test
    @DisplayName("candles가 empty면 예외 발생")
    void calculate_emptyCandles_throwsException() {
        // when & then
        var calculator = new EmaCalculator(EmaParams.SHORT);
        assertThatThrownBy(() -> calculator.calculate(List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Candles cannot be null or empty");
    }

    @Test
    @DisplayName("데이터가 period보다 적으면 예외 발생")
    void calculate_notEnoughData_throwsException() {
        // given
        var candles = createCandles(100.0, 110.0, 120.0, 130.0, 140.0, 150.0, 160.0, 170.0);  // 8개
        var calculator = new EmaCalculator(EmaParams.SHORT);

        // when & then
        assertThatThrownBy(() -> calculator.calculate(candles))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Not enough data")
                .hasMessageContaining("Required: 9")
                .hasMessageContaining("Provided: 8");
    }

    @Test
    @DisplayName("EMA는 연속 계산 시 이전 값에 영향")
    void ema_dependsOnPreviousValues() {
        // given
        // 동일한 최근 3개 종가지만, 이전 데이터가 다른 경우
        var candles1 = createCandles(
                50.0, 60.0, 70.0, 100.0, 100.0, 100.0,
                100.0, 100.0, 100.0, 100.0, 100.0, 100.0
        );
        var candles2 = createCandles(
                150.0, 140.0, 130.0, 100.0, 100.0, 100.0,
                100.0, 100.0, 100.0, 100.0, 100.0, 100.0
        );

        // when
        var ema1 = calculator.calculate(candles1);
        var ema2 = calculator.calculate(candles2);

        // then
        // 최근 3개는 동일하지만, 초기 SMA가 다르므로 최종 EMA도 다름
        assertThat(ema1.value()).isNotEqualByComparingTo(ema2.value());
    }

    private List<Candle> createCandles(double... closePrices) {
        return IntStream.range(0, closePrices.length)
                .mapToObj(i -> createCandle(closePrices[i], 1000L * (i + 1)))
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
        return new OHLCV(
                new Price(BigDecimal.valueOf(closePrice - 5)),
                new Price(BigDecimal.valueOf(closePrice + 5)),
                new Price(BigDecimal.valueOf(closePrice - 10)),
                new Price(BigDecimal.valueOf(closePrice)),
                new Volume(BigDecimal.valueOf(1000)),
                Price.from(5000)
        );
    }
}
