package com.example.acquisition.application;

import com.example.acquisition.domain.Aggregator;
import com.example.acquisition.domain.Candle;
import com.example.core.enums.Timeframe;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GetDailyItems {
    private final Aggregator<Candle> candleAggregator;
    private final PeriodCandlesFinder periodCandlesFinder;

    public GetDailyItems(
            Aggregator<Candle> candleAggregator,
            PeriodCandlesFinder periodCandlesFinder
    ) {
        this.candleAggregator = candleAggregator;
        this.periodCandlesFinder = periodCandlesFinder;
    }

    public List<DailyItem> execute(DailyItemsCriteria criteria) {
        List<Candle> candles = periodCandlesFinder.find(criteria.toPeriodCandlesQuery());

        long dayMillis = Timeframe.DAYS.getSeconds() * 1000L;
        return candleAggregator.aggregate(candles, Timeframe.HOURS)
                .stream()
                .collect(Collectors.groupingBy(
                        c -> c.startTime().value() / dayMillis,
                        LinkedHashMap::new,
                        Collectors.toList()
                )).entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey()) // 키 오름차순
                .map(e -> new DailyItem(criteria.symbol(), criteria.currency(), e.getValue().stream()
                        .map(HourlyCandle::from)
                        .toList()))
                .toList();
    }
}
