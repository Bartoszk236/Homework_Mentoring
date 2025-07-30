package com.example.RateLimitingAndMonitoring.dto;

import lombok.Builder;

@Builder
public record MetricResponse(
        String endpointURI,
        Integer requestCounter,
        String errorRate,
        MetricResponseTime responseTime
) {
}
