package com.example.acquisition.infra;

import com.example.acquisition.application.CandlesFinder;
import com.example.acquisition.application.CandlesQuery;
import com.example.acquisition.domain.Candle;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CandleJpaAdapter implements CandlesFinder {
    private final CandleJpaRepository jpaRepository;

    public CandleJpaAdapter(CandleJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Candle> find(CandlesQuery query) {
        return jpaRepository.findAllBySymbolAndCurrencyAndStartTimeBetween(
                query.symbol().value(),
                query.currency().getCurrencyCode(),
                query.start().toDateTime(),
                query.end().toDateTime()
        ).stream().map(CandleEntity::toModel).toList();
    }
}
