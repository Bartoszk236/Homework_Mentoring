package com.example.WeatherDataAggregationSystem.service;

import com.example.WeatherDataAggregationSystem.client.OpenWeatherMapClient;
import com.example.WeatherDataAggregationSystem.client.WeatherApiClient;
import com.example.WeatherDataAggregationSystem.data.AggregatedWeather;
import com.example.WeatherDataAggregationSystem.mapper.AggregatedWeatherMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final OpenWeatherMapClient openWeatherMapClient;
    private final WeatherApiClient weatherApiClient;
    private final AggregatedWeatherMapper aggregatedWeatherMapper;

    @Cacheable(cacheNames = "weather", key = "#city")
    public Mono<AggregatedWeather> getWeatherForCity(String city) {
        return Mono.zip(
                        openWeatherMapClient.getWeatherByCityName(city),
                        weatherApiClient.getWeatherByCityName(city)
                )
                .map(tuple ->
                        aggregatedWeatherMapper.toAggregatedWeather(List.of(tuple.getT1(), tuple.getT2()))
                );
    }
}
