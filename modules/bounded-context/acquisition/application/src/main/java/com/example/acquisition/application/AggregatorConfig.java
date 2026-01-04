package com.example.acquisition.application;

import com.example.acquisition.domain.Aggregator;
import com.example.acquisition.domain.Candle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AggregatorConfig {
    @Bean
    public Aggregator<Candle> candleAggregator() {
        return new Aggregator<>();
    }
}
