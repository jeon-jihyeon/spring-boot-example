package com.example.acquisition.infra;

import com.example.acquisition.domain.Candle;
import com.example.core.enums.Timeframe;
import com.example.core.values.*;
import com.example.infrastructure.BaseJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

/**
 * 분단위 캔들 데이터 엔티티
 * 분봉만 저장하고, 다른 캔들 타입(초, 일, 주, 월, 년)은 분봉으로 계산
 * 분봉 candle의 unit(1,3,5,10,15,30,60,240)에서 unit=1만 사용
 */
@Entity
public class CandleEntity extends BaseJpaEntity {
    /**
     * 자산 심볼 (BTC, AAPL, GOLD 등)
     */
    @Column(name = "symbol", length = 16, nullable = false)
    private String symbol;
    /**
     * 화폐 단위
     */
    @Column(name = "currency", length = 3, nullable = false)
    private String currency;
    /**
     * 캔들 구간의 시작 시각 (UTC 기준)
     */
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;
    /**
     * 시가. 해당 캔들의 첫 거래 가격
     */
    @Column(name = "open", precision = 30, scale = 8, nullable = false)
    private BigDecimal open;
    /**
     * 고가. 해당 캔들의 최고 거래 가격
     */
    @Column(name = "high", precision = 30, scale = 8, nullable = false)
    private BigDecimal high;
    /**
     * 저가. 해당 캔들의 최저 거래 가격
     */
    @Column(name = "low", precision = 30, scale = 8, nullable = false)
    private BigDecimal low;
    /**
     * 종가. 해당 페어의 현재 가격
     */
    @Column(name = "close", precision = 30, scale = 8, nullable = false)
    private BigDecimal close;
    /**
     * 해당 캔들 동안의 누적 거래된 자산의 수량
     */
    @Column(name = "volume", precision = 20, scale = 8, nullable = false)
    private BigDecimal volume;
    /**
     * 해당 캔들 동안의 누적 거래 금액
     */
    @Column(name = "turnover", precision = 40, scale = 8, nullable = false)
    private BigDecimal turnover;

    public CandleEntity() {
    }

    public CandleEntity(Candle model) {
        this.symbol = model.symbol().value();
        this.currency = model.currency().getCurrencyCode();
        this.startTime = model.startTime().toDateTime();
        this.open = model.ohlcv().open().value();
        this.high = model.ohlcv().high().value();
        this.low = model.ohlcv().low().value();
        this.close = model.ohlcv().close().value();
        this.volume = model.ohlcv().volume().value();
        this.turnover = model.ohlcv().turnover().value();
    }

    public Candle toModel() {
        OHLCV ohlcv = new OHLCV(
                new Price(open),
                new Price(high),
                new Price(low),
                new Price(close),
                new Volume(volume),
                new Price(turnover)
        );
        return new Candle(
                new Symbol(symbol),
                Currency.getInstance(currency),
                EpochMillis.from(startTime),
                ohlcv,
                Timeframe.MINUTES
        );
    }
}