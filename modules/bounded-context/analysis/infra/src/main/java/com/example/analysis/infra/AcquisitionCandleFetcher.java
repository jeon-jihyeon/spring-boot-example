package com.example.analysis.infra;

import com.example.analysis.application.CandleFetcher;
import com.example.analysis.application.CandlesRequest;
import com.example.analysis.domain.Candle;
import com.example.contract.acquisition.AcquisitionCandleResponse;
import com.example.contract.acquisition.AcquisitionCandlesRequest;
import com.example.contract.acquisition.AcquisitionContract;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AcquisitionCandleFetcher implements CandleFetcher {
    private final AcquisitionContract acquisitionContract;

    public AcquisitionCandleFetcher(AcquisitionContract acquisitionContract) {
        this.acquisitionContract = acquisitionContract;
    }

    @Override
    public List<Candle> find(CandlesRequest request) {
        return acquisitionContract.findAcquisitionCandles(toRequest(request)).stream().map(this::toCandle).toList();
    }

    private AcquisitionCandlesRequest toRequest(CandlesRequest request) {
        return new AcquisitionCandlesRequest(request.symbol(), request.currency(), request.start(), request.end());
    }

    private Candle toCandle(AcquisitionCandleResponse response) {
        return new Candle(response.symbol(), response.startTime(), response.ohlcv(), response.timeframe());
    }
}
