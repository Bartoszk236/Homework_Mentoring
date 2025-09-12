package com.example.WeatherDataAggregationSystem.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AggregatedWeather {
    private String city;
    private List<WeatherData> sources;
    private Double averageTemperature;
    private LocalDateTime lastUpdate;
}
