package com.example.WeatherDataAggregationSystem.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherData {
    private String city;
    private Double temperature;
    private String description;
    private String source;
    private LocalDateTime timestamp;
}
