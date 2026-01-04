package com.example.core.values;

/**
 * OHLCV (Open, High, Low, Close, Volume) 값을 나타내는 불변 객체
 *
 * @param open     시가
 * @param high     고가
 * @param low      저가
 * @param close    종가
 * @param volume   거래량
 * @param turnover 거래대금
 */
public record OHLCV(Price open, Price high, Price low, Price close, Volume volume, Price turnover) {
    public OHLCV {
        if (open == null || high == null || low == null || close == null || volume == null || turnover == null) {
            throw new IllegalArgumentException("OHLCV values cannot be null");
        }
        if (high.isLessThan(low)) {
            throw new IllegalArgumentException("High price must be greater than or equal to low price");
        }
    }
}
