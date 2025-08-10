package com.example.WeatherDataAggregationSystem.mapper;

import com.example.WeatherDataAggregationSystem.data.AggregatedWeather;
import com.example.WeatherDataAggregationSystem.data.WeatherData;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class AggregatedWeatherMapper {

    public AggregatedWeather toAggregatedWeather(List<WeatherData> sources) {
        double sumTemperature = sources.stream()
                .mapToDouble(WeatherData::getTemperature)
                .sum();

        BigDecimal avg = BigDecimal.valueOf(sumTemperature)
                .divide(BigDecimal.valueOf(sources.size()), 2 , RoundingMode.HALF_UP);

        return AggregatedWeather.builder()
                .sources(sources)
                .city(sources.getFirst().getCity())
                .averageTemperature(avg.doubleValue())
                .lastUpdate(LocalDateTime.now())
                .build();
    }
}
