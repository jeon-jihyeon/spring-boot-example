package com.example.contract.acquisition;

import com.example.core.enums.Timeframe;
import com.example.core.values.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;

class AcquisitionCandleResponseTest {

    @Test
    @DisplayName("생성 - 유효한 값으로 생성")
    void constructor_validValues_createsResponse() {
        // given
        var symbol = Symbol.from("BTC");
        var currency = Currency.getInstance("USD");
        var startTime = EpochMillis.from(1000L);
        var ohlcv = new OHLCV(
                Price.from("100"),
                Price.from("150"),
                Price.from("90"),
                Price.from("120"),
                Volume.from("1000"),
                Price.from("5000")
        );
        var timeframe = Timeframe.HOURS;

        // when
        var response = new AcquisitionCandleResponse(symbol, currency, startTime, ohlcv, timeframe);

        // then
        assertThat(response.symbol()).isEqualTo(symbol);
        assertThat(response.currency()).isEqualTo(currency);
        assertThat(response.startTime()).isEqualTo(startTime);
        assertThat(response.ohlcv()).isEqualTo(ohlcv);
        assertThat(response.timeframe()).isEqualTo(timeframe);
    }
}
