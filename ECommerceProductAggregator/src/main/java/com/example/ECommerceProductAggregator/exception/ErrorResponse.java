package com.example.ECommerceProductAggregator.exception;

public record ErrorResponse(
        int status,
        String message
) {
}
