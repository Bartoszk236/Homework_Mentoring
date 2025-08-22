package com.example.ECommerceProductAggregator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "api.url")
public record VariablesProps(
        String euroNetUrl,
        String mediaExpertUrl,
        String mediaMarktUrl,
        String nbpUrl
) {
}
