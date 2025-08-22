package com.example.ECommerceProductAggregator.dto;

import java.util.List;

public record Product(
        String id,
        String name,
        String description,
        List<ProductOffer> offers,
        List<Review> reviews
) {
}
