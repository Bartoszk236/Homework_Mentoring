package com.example.ECommerceProductAggregator.dto;

import java.math.BigDecimal;
import java.util.List;

public record ExternalApiResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        String currency,
        Boolean inStock,
        String shopName,
        List<ReviewResponse> reviews
) {
    public record ReviewResponse(
            Long id,
            String username,
            String comment
    ) {
    }
}
