package com.example.RateLimitingAndMonitoring.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@EnableConfigurationProperties(RateLimitConfig.Properties.class)
public class RateLimitConfig {
    private final Properties properties;

    public RateLimitConfig(Properties properties) {
        this.properties = properties;
    }

    public int getLimitForUnauthorizedUser() {
        return properties.getLimitForUnauthorizedUser();
    }

    public int getLimitForAuthorizedUser() {
        return properties.getLimitForAuthorizedUser();
    }

    public int getLimitForPremiumUser() {
        return properties.getLimitForPremiumUser();
    }

    public Duration getWindowDuration() {
        return properties.getWindow();
    }

    @ConfigurationProperties(prefix = "rate.limit")
    @Getter
    @Setter
    public static class Properties {
        private int limitForUnauthorizedUser = 100;
        private int limitForAuthorizedUser = 1_000;
        private int limitForPremiumUser = 10_000;
        private Duration window = Duration.ofSeconds(60);
    }
}
