package com.example.analysis.domain.indicator;

public enum Code {
    // Tier 1
    SMA("Simple Moving Average"),
    EMA("Exponential Moving Average"),
    RSI("Relative Strength Index"),
    MACD("Moving Average Convergence Divergence"),
    BB("Bollinger Bands"),

    // Tier 2
    ATR("Average True Range"),
    VWAP("Volume Weighted Average Price"),
    STOCH("Stochastic Oscillator"),
    OBV("On-Balance Volume"),
    ADX("Average Directional Index"),

    // Tier 3
    CCI("Commodity Channel Index"),
    MFI("Money Flow Index"),
    WMA("Weighted Moving Average"),
    ROC("Rate of Change"),
    WR("Williams %R"),

    // Tier 4
    AROON("Aroon"),
    DMI("Directional Movement Index"),
    TRIX("Triple Exponential Average"),
    KC("Keltner Channels"),
    DC("Donchian Channels"),
    SD("Standard Deviation"),
    AD("Accumulation/Distribution"),
    CMF("Chaikin Money Flow"),
    RVI("Relative Vigor Index"),
    MOM("Momentum"),
    DPO("Detrended Price Oscillator"),
    STC("Schaff Trend Cycle"),

    // Tier 5
    // Volume Profiles
    VP("Volume Profile"),
    VPVR("Volume Profile Visible Range"),
    VPOC("Volume Point of Control"),
    VA("Value Area"),

    // Market Profiles
    MP("Market Profile"),
    PP("Pivot Points"),
    FIB("Fibonacci Retracement"),
    CAM("Camarilla"),

    // Cycle Analysis
    HILBERT("Hilbert Transform"),
    FOURIER("Fourier Cycle"),
    SINEWAVE("Ehlers Sinewave"),

    // Market Breadth
    ADL("Advance/Decline Line"),
    MCCL("McClellan Oscillator"),
    TRIN("Arms Index"),
    MMTH("Percent of Stocks Above 200-Day Average"),
    NHNL("New Highs/New Lows"),

    // Sentiment Indicators
    PCR("Put/Call Ratio"),
    VIX("CBOE Volatility Index"),
    AAII("AAII Sentiment Survey"),
    FGI("Fear and Greed Index"),
    COT("Commitment of Traders");

    private final String fullName;

    Code(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }
}
