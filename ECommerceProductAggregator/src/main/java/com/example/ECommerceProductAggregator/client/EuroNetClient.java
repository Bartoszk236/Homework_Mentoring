package com.example.ECommerceProductAggregator.client;

import com.example.ECommerceProductAggregator.dto.ExternalApiResponse;
import com.example.ECommerceProductAggregator.exception.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@Slf4j
public class EuroNetClient {
    private final RestClient restClient;

    public EuroNetClient(@Qualifier("euroNet") RestClient restClient) {
        this.restClient = restClient;
    }

    @Cacheable(
            cacheNames = "offers",
            key = "#productName.toLowerCase() + 'euroNet'",
            unless = "#result == null || #result.isEmpty()"
    )
    public List<ExternalApiResponse> getProduct(String productName) {
        return restClient.get()
                .uri("/" + productName)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    log.error("Error retrieving EuroNet product: {}", productName);
                    throw new ProductNotFoundException("Product: " + productName + "not found");
                })
                .body(new ParameterizedTypeReference<>() {
                });
    }
}
