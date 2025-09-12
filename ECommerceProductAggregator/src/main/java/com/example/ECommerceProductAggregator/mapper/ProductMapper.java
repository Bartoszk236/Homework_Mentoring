package com.example.ECommerceProductAggregator.mapper;

import com.example.ECommerceProductAggregator.dto.ExternalApiResponse;
import com.example.ECommerceProductAggregator.dto.ProductOffer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {
    public List<ProductOffer> toProductOffers(List<ExternalApiResponse> responses) {
        return responses.stream()
                .map(response -> new ProductOffer(
                        response.shopName(),
                        response.price(),
                        response.currency(),
                        response.inStock(),
                        "localhost:8100/product/" + response.id()
                ))
                .collect(Collectors.toList());
    }
}
