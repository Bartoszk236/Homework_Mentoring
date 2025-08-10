package com.example.WeatherDataAggregationSystem.dto;

import com.example.WeatherDataAggregationSystem.data.WeatherData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenWeatherMapResponse(
        @JsonProperty("weather")
        List<WeatherNested> weather,
        @JsonProperty("name")
        String city,
        @JsonProperty("main")
        MainNested main
) {
    public WeatherData toWeatherData() {
        return WeatherData.builder()
                .city(this.city)
                .temperature(main().temperature)
                .description(weather.getFirst().description)
                .source("OpenWeatherMap")
                .timestamp(LocalDateTime.now())
                .build();
    }

    public record WeatherNested(
            @JsonProperty("description")
            String description
    ) {
    }

    public record MainNested(
            @JsonProperty("temp")
            Double temperature
    ) {
    }
}
