package com.example.analysis.application;

import java.util.List;

public interface DailyItemsFetcher {
    List<DailyItem> find(DailyItemsRequest request);
}
