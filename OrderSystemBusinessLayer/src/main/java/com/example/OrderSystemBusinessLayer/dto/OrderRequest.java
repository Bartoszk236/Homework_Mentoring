package com.example.OrderSystemBusinessLayer.dto;

import java.util.List;

public record OrderRequest(
        List<OrderItemRequest> orderItemRequest,
        List<String> discountCodes,
        Long customerId
) {
}
