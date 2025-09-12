package com.example.ECommerceProductAggregator.client;

import com.example.ECommerceProductAggregator.dto.CurrencyRateResponse;
import com.example.ECommerceProductAggregator.exception.CurrencyRatesException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@Slf4j
public class NbpClient {
    private final RestClient client;

    public NbpClient(@Qualifier("apiNbp") RestClient restClient) {
        this.client = restClient;
    }

    @Cacheable(
            cacheNames = "currencies",
            key = "#currencyCode",
            unless = "#result == null"
    )
    public CurrencyRateResponse getRates(String currencyCode) {
        return client.get()
                .uri("/" + currencyCode)
                .header("Accept", "application/json")
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    log.error("Error retrieving currency rates from Nbp API for: {}", currencyCode);
                    throw new CurrencyRatesException("Error while retrieving currency rates from Nbp API");
                })
                .body(CurrencyRateResponse.class);
    }
}
