package com.example.acquisition.infra;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CandleJpaRepository extends JpaRepository<CandleEntity, Long> {
    List<CandleEntity> findAllBySymbolAndCurrencyAndStartTimeBetween(
            String symbol, String currency, LocalDateTime start, LocalDateTime end
    );
}
