package com.example.ECommerceProductAggregator.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        var offers = new CaffeineCacheManager("offers");
        offers.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofMinutes(2))
                .maximumSize(500));

        var currencies = new CaffeineCacheManager("currencies");
        currencies.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofHours(12))
                .maximumSize(500));

        return new CompositeCacheManager(offers, currencies);
    }
}
