package com.example.acquisition.application;

import com.example.acquisition.domain.CandleAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AggregatorConfig {
    @Bean
    public CandleAggregator candleAggregator() {
        return new CandleAggregator();
    }
}
