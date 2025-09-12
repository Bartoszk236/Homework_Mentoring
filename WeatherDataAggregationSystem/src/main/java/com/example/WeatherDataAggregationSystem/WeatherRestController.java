package com.example.WeatherDataAggregationSystem;

import com.example.WeatherDataAggregationSystem.data.AggregatedWeather;
import com.example.WeatherDataAggregationSystem.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
class WeatherRestController {
    private final WeatherService service;

    @GetMapping
    public Mono<AggregatedWeather> getWeatherForCity(@RequestParam String city) {
        return service.getWeatherForCity(city);
    }
}
