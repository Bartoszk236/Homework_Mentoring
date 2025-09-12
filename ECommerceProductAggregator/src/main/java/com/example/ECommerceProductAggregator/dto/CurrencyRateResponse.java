package com.example.ECommerceProductAggregator.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CurrencyRateResponse(
        @JsonProperty("rates")
        List<Rates> rates
) {
    public BigDecimal getRate() {
        return rates.getFirst().rate();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Rates(
            @JsonProperty("mid")
            BigDecimal rate
    ) {
    }
}
