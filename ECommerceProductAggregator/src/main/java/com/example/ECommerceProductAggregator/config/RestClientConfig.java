package com.example.ECommerceProductAggregator.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@Slf4j
@EnableConfigurationProperties(VariablesProps.class)
@RequiredArgsConstructor
public class RestClientConfig {
    private final VariablesProps properties;

    @Bean
    @Qualifier("euroNet")
    public RestClient euroNetClientConfig() {
        return buildClient(properties.euroNetUrl());
    }

    @Bean
    @Qualifier("mediaExpert")
    public RestClient mediaExpertClientConfig() {
        return buildClient(properties.mediaExpertUrl());
    }

    @Bean
    @Qualifier("mediaMarkt")
    public RestClient mediaMarktClientConfig() {
        return buildClient(properties.mediaMarktUrl());
    }

    @Bean
    @Qualifier("apiNbp")
    public RestClient apiNbpClientConfig() {
        return buildClient(properties.nbpUrl());
    }

    private RestClient buildClient(String baseUrl) {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .requestInterceptor((request, body, execution) -> {
                    log.info("Sending request: method: {}, uri: {}", request.getMethod(), request.getURI());
                    return execution.execute(request, body);
                })
                .build();
    }
}
