package com.example.acquisition.api;

import com.example.acquisition.application.DailyItem;
import com.example.acquisition.application.DailyItemsCriteria;
import com.example.acquisition.application.GetDailyItems;
import com.example.acquisition.application.HourlyCandle;
import com.example.contracts.acquisition.AcquisitionContract;
import com.example.contracts.acquisition.DailyAcquisitionRequest;
import com.example.contracts.acquisition.DailyAcquisitionResponse;
import com.example.contracts.acquisition.HourlyCandleResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AcquisitionFacade implements AcquisitionContract {
    private final GetDailyItems getDailyItems;

    public AcquisitionFacade(GetDailyItems getDailyItems) {
        this.getDailyItems = getDailyItems;
    }

    private static DailyItemsCriteria toCriteria(DailyAcquisitionRequest request) {
        return new DailyItemsCriteria(request.symbol(), request.currency(), request.start(), request.end());
    }

    private HourlyCandleResponse toCandleResponse(HourlyCandle data) {
        return new HourlyCandleResponse(data.symbol(), data.currency(), data.startTime(), data.ohlcv());
    }

    private DailyAcquisitionResponse toResponse(DailyItem data) {
        return new DailyAcquisitionResponse(
                data.symbol(), data.currency(),
                data.hourlyCandles().stream().map(this::toCandleResponse).toList()
        );
    }

    @Override
    public List<DailyAcquisitionResponse> findDailyAcquisitions(DailyAcquisitionRequest request) {
        return getDailyItems.execute(toCriteria(request)).stream().map(this::toResponse).toList();
    }
}
