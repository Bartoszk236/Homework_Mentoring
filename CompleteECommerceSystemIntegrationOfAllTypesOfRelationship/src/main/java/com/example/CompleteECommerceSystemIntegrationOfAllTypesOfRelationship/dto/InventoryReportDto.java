package com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.dto;

import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public record InventoryReportDto(
        List<Product> lowStocksProducts,
        Product mostFrequentlyPurchasedProduct,
        BigDecimal totalStockValue
) {
}
