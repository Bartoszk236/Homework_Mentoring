package com.example.ECommerceProductAggregator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public record ExternalApiResponse(
        @JsonProperty("id")
        Long id,
        @JsonProperty("name")
        String name,
        @JsonProperty("description")
        String description,
        @JsonProperty("price")
        BigDecimal price,
        @JsonProperty("currency")
        String currency,
        @JsonProperty("inStock")
        Boolean inStock,
        @JsonProperty("shopName")
        String shopName,
        @JsonProperty("reviews")
        List<ReviewResponse> reviews
) {
    public record ReviewResponse(
            @JsonProperty("id")
            Long id,
            @JsonProperty("username")
            String username,
            @JsonProperty("comment")
            String comment
    ) {
    }
}
