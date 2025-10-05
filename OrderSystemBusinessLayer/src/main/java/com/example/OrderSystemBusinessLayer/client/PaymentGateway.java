package com.example.OrderSystemBusinessLayer.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentGateway {
    private final RestClient restClient;

    public int initPayment(String orderUUID, BigDecimal amount) {
        ResponseEntity<?> response = restClient.post()
                .uri("/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of(
                        "orderUUID", orderUUID,
                        "amount", amount
                ))
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, httpResponse) ->
                        log.error("Error while call external payment gateway"))
                .toEntity(Object.class);
        return response.getStatusCode().value();
    }
}
