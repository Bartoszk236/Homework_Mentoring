package com.example.OrderSystemBusinessLayer.dto;

public record OrderItemRequest(
        Long productId,
        Integer quantity
) {
}
