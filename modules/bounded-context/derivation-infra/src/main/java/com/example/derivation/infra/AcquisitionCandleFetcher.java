package com.example.derivation.infra;

import com.example.contract.acquisition.AcquisitionCandleResponse;
import com.example.contract.acquisition.AcquisitionCandlesRequest;
import com.example.contract.acquisition.AcquisitionContract;
import com.example.derivation.application.CandleFetcher;
import com.example.derivation.application.CandlesRequest;
import com.example.derivation.domain.Candle;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AcquisitionCandleFetcher implements CandleFetcher {
    private final AcquisitionContract acquisitionContract;

    public AcquisitionCandleFetcher(AcquisitionContract acquisitionContract) {
        this.acquisitionContract = acquisitionContract;
    }

    @Override
    public List<Candle> find(CandlesRequest candlesRequest) {
        var request = new AcquisitionCandlesRequest(candlesRequest.symbol(), candlesRequest.currency(), candlesRequest.start(), candlesRequest.end());
        return acquisitionContract.findAcquisitionCandles(request).stream().map(this::toCandle).toList();
    }

    private Candle toCandle(AcquisitionCandleResponse response) {
        return new Candle(response.symbol(), response.startTime(), response.ohlcv(), response.timeframe());
    }
}
