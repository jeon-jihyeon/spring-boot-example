package com.example.derivation.api;

import com.example.core.values.EpochMillis;
import com.example.core.values.Symbol;
import com.example.derivation.application.GetStandardMacd;
import com.example.derivation.application.IndicatorParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Currency;

@RestController
@RequestMapping("/api/derivation")
public class DerivationController {
    private final GetStandardMacd getStandardMacd;

    public DerivationController(GetStandardMacd getStandardMacd) {
        this.getStandardMacd = getStandardMacd;
    }

    @GetMapping("/macd")
    public IndicatorResponse getMacd(IndicatorRequest request) {
        var indicator = getStandardMacd.execute(new IndicatorParam(
                new Symbol(request.symbol()),
                Currency.getInstance(request.currency()),
                new EpochMillis(request.start()),
                new EpochMillis(request.end())
        ));
        return IndicatorResponse.from(indicator);
    }
}
