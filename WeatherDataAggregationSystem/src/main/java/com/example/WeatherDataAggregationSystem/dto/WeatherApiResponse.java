package com.example.WeatherDataAggregationSystem.dto;

import com.example.WeatherDataAggregationSystem.data.WeatherData;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record WeatherApiResponse(
        @JsonProperty("location")
        LocationNested location,
        @JsonProperty("current")
        CurrentNested current

) {
    public WeatherData toWeatherData() {
        return WeatherData.builder()
                .city(location().city)
                .temperature(current().temperature())
                .description(current().condition().description)
                .source("WeatherApi")
                .timestamp(LocalDateTime.now())
                .build();
    }

    public record LocationNested(
            @JsonProperty("name")
            String city
    ) {
    }

    public record CurrentNested(
            @JsonProperty("condition")
            ConditionNested condition,
            @JsonProperty("temp_c")
            Double temperature
    ) {
    }

    public record ConditionNested(
            @JsonProperty("text")
            String description
    ) {
    }
}
