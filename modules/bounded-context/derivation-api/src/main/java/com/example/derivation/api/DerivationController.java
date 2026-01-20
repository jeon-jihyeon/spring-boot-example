package com.example.derivation.api;

import com.example.core.values.EpochMillis;
import com.example.core.values.Symbol;
import com.example.derivation.application.CandlesRequest;
import com.example.derivation.application.GetIndicators;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Currency;
import java.util.List;

@RestController
@RequestMapping("/api/derivation")
public class DerivationController {
    private final GetIndicators getIndicators;

    public DerivationController(GetIndicators getIndicators) {
        this.getIndicators = getIndicators;
    }

    @GetMapping("/indicators")
    public List<IndicatorResponse> getIndicators(IndicatorRequest request) {
        return getIndicators.execute(new CandlesRequest(
                new Symbol(request.symbol()),
                Currency.getInstance(request.currency()),
                new EpochMillis(request.start()),
                new EpochMillis(request.end())
        )).stream().map(IndicatorResponse::from).toList();
    }
}
