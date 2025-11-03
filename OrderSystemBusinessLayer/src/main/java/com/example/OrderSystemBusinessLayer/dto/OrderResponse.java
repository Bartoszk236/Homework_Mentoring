package com.example.OrderSystemBusinessLayer.dto;

import java.math.BigDecimal;

public record OrderResponse(
        Long orderId,
        BigDecimal totalVale
) {
}
