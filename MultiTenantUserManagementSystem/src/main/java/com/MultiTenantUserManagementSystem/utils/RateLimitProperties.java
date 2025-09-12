package com.MultiTenantUserManagementSystem.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "rate.limit")
public record RateLimitProperties(
        int capacity,
        int refillTokens,
        Duration refillPeriod,
        int bulkCost
) {
}
