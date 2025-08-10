package com.example.WeatherDataAggregationSystem.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @Qualifier("apiOpenWeatherMap")
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .baseUrl("https://api.openweathermap.org/data/2.5/weather")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.USER_AGENT, "Backend-app")
                .build();
    }

    @Bean
    @Qualifier("weatherApi")
    public WebClient weatherApi(WebClient.Builder builder) {
        return builder
                .baseUrl("http://api.weatherapi.com/v1/current.json")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.USER_AGENT, "Backend-app")
                .build();
    }
}
