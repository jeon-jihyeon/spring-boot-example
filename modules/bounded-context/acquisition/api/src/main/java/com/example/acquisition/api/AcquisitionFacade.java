package com.example.acquisition.api;

import com.example.acquisition.application.GetCandles;
import com.example.acquisition.application.GetCandlesRequest;
import com.example.acquisition.domain.Candle;
import com.example.contract.acquisition.AcquisitionCandleResponse;
import com.example.contract.acquisition.AcquisitionCandlesRequest;
import com.example.contract.acquisition.AcquisitionContract;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AcquisitionFacade implements AcquisitionContract {
    private final GetCandles getCandles;

    public AcquisitionFacade(GetCandles getCandles) {
        this.getCandles = getCandles;
    }

    private static GetCandlesRequest toCriteria(AcquisitionCandlesRequest request) {
        return new GetCandlesRequest(request.symbol(), request.currency(), request.start(), request.end());
    }

    private AcquisitionCandleResponse toResponse(Candle model) {
        return new AcquisitionCandleResponse(
                model.symbol(),
                model.currency(),
                model.startTime(),
                model.ohlcv(),
                model.timeframe()
        );
    }

    @Override
    public List<AcquisitionCandleResponse> findAcquisitionCandles(AcquisitionCandlesRequest request) {
        return getCandles.execute(toCriteria(request)).stream().map(this::toResponse).toList();
    }
}
