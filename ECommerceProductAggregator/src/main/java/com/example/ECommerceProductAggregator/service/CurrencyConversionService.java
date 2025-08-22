package com.example.ECommerceProductAggregator.service;

import com.example.ECommerceProductAggregator.client.NbpClient;
import com.example.ECommerceProductAggregator.dto.CurrencyRateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class CurrencyConversionService {
    private final NbpClient nbpClient;

    public BigDecimal convertToPln(BigDecimal amount, String currencyFrom) {
        CurrencyRateResponse response = nbpClient.getRates(currencyFrom);
        BigDecimal rate = response.getRate();
        return amount.multiply(rate).setScale(2, RoundingMode.HALF_UP);
    }
}
