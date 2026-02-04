package com.example.core.values;

import com.example.core.exception.InvalidValueException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OHLCVTest {

    @Test
    @DisplayName("생성 - 유효한 값으로 생성")
    void constructor_validValues_createsOHLCV() {
        // given
        var open = Price.from("100");
        var high = Price.from("150");
        var low = Price.from("90");
        var close = Price.from("120");
        var volume = Volume.from("1000");
        var turnover = Price.from("5000");

        // when
        var ohlcv = new OHLCV(open, high, low, close, volume, turnover);

        // then
        assertThat(ohlcv.open()).isEqualTo(open);
        assertThat(ohlcv.high()).isEqualTo(high);
        assertThat(ohlcv.low()).isEqualTo(low);
        assertThat(ohlcv.close()).isEqualTo(close);
        assertThat(ohlcv.volume()).isEqualTo(volume);
        assertThat(ohlcv.turnover()).isEqualTo(turnover);
    }

    @Test
    @DisplayName("생성 - open이 null이면 예외 발생")
    void constructor_nullOpen_throwsException() {
        assertThatThrownBy(() -> new OHLCV(
                null,
                Price.from("150"),
                Price.from("90"),
                Price.from("120"),
                Volume.from("1000"),
                Price.from("5000")
        ))
                .isInstanceOf(InvalidValueException.class)
                .hasMessage("OHLCV values cannot be null");
    }

    @Test
    @DisplayName("생성 - high가 null이면 예외 발생")
    void constructor_nullHigh_throwsException() {
        assertThatThrownBy(() -> new OHLCV(
                Price.from("100"),
                null,
                Price.from("90"),
                Price.from("120"),
                Volume.from("1000"),
                Price.from("5000")
        ))
                .isInstanceOf(InvalidValueException.class)
                .hasMessage("OHLCV values cannot be null");
    }

    @Test
    @DisplayName("생성 - low가 null이면 예외 발생")
    void constructor_nullLow_throwsException() {
        assertThatThrownBy(() -> new OHLCV(
                Price.from("100"),
                Price.from("150"),
                null,
                Price.from("120"),
                Volume.from("1000"),
                Price.from("5000")
        ))
                .isInstanceOf(InvalidValueException.class)
                .hasMessage("OHLCV values cannot be null");
    }

    @Test
    @DisplayName("생성 - close가 null이면 예외 발생")
    void constructor_nullClose_throwsException() {
        assertThatThrownBy(() -> new OHLCV(
                Price.from("100"),
                Price.from("150"),
                Price.from("90"),
                null,
                Volume.from("1000"),
                Price.from("5000")
        ))
                .isInstanceOf(InvalidValueException.class)
                .hasMessage("OHLCV values cannot be null");
    }

    @Test
    @DisplayName("생성 - volume이 null이면 예외 발생")
    void constructor_nullVolume_throwsException() {
        assertThatThrownBy(() -> new OHLCV(
                Price.from("100"),
                Price.from("150"),
                Price.from("90"),
                Price.from("120"),
                null,
                Price.from("5000")
        ))
                .isInstanceOf(InvalidValueException.class)
                .hasMessage("OHLCV values cannot be null");
    }

    @Test
    @DisplayName("생성 - turnover가 null이면 예외 발생")
    void constructor_nullTurnover_throwsException() {
        assertThatThrownBy(() -> new OHLCV(
                Price.from("100"),
                Price.from("150"),
                Price.from("90"),
                Price.from("120"),
                Volume.from("1000"),
                null
        ))
                .isInstanceOf(InvalidValueException.class)
                .hasMessage("OHLCV values cannot be null");
    }

    @Test
    @DisplayName("생성 - high가 low보다 작으면 예외 발생")
    void constructor_highLessThanLow_throwsException() {
        assertThatThrownBy(() -> new OHLCV(
                Price.from("100"),
                Price.from("80"),  // high < low
                Price.from("90"),
                Price.from("85"),
                Volume.from("1000"),
                Price.from("5000")
        ))
                .isInstanceOf(InvalidValueException.class)
                .hasMessage("High price must be greater than or equal to low price");
    }

    @Test
    @DisplayName("생성 - high와 low가 같으면 유효")
    void constructor_highEqualsLow_createsOHLCV() {
        // given
        var samePrice = Price.from("100");

        // when
        var ohlcv = new OHLCV(
                Price.from("100"),
                samePrice,
                samePrice,
                Price.from("100"),
                Volume.from("1000"),
                Price.from("5000")
        );

        // then
        assertThat(ohlcv.high()).isEqualTo(samePrice);
        assertThat(ohlcv.low()).isEqualTo(samePrice);
    }
}
