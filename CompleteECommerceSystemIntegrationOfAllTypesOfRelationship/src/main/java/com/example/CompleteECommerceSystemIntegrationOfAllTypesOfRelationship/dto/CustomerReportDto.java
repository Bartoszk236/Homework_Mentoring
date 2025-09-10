package com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.dto;

import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.entity.Category;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CustomerReportDto(
        BigDecimal totalOrdersValue,
        Integer ordersCount,
        Category favouriteCategory,
        LocalDateTime lastActivity
) {
}
