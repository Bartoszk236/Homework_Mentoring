package com.example.RateLimitingAndMonitoring.dto;

import lombok.Builder;

@Builder
public record MetricResponseTime(
        String unit,
        long average,
        long P50,
        long P95,
        long P99
) {
}
