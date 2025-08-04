package com.example.JsonDeserializationWithCustomLogic.dto;

import java.math.BigDecimal;

public record OrderItem(
        Long productId,
        Integer quantity,
        BigDecimal unitPrice
) {
}
