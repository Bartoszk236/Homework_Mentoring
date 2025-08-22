package com.example.ECommerceProductAggregator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductOffer {
    private String shopName;
    private BigDecimal price;
    private String currency;
    private Boolean inStock;
    private String url;
}
