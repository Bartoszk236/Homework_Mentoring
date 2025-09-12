package com.example.WeatherDataAggregationSystem.service;

import com.example.WeatherDataAggregationSystem.client.OpenWeatherMapClient;
import com.example.WeatherDataAggregationSystem.client.WeatherApiClient;
import com.example.WeatherDataAggregationSystem.data.AggregatedWeather;
import com.example.WeatherDataAggregationSystem.data.WeatherData;
import com.example.WeatherDataAggregationSystem.mapper.AggregatedWeatherMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {
    @Mock
    private OpenWeatherMapClient openWeatherMapClient;
    @Mock
    private WeatherApiClient weatherApiClient;
    @Mock
    private AggregatedWeatherMapper aggregatedWeatherMapper;
    @InjectMocks
    private WeatherService weatherService;

    @Test
    void getWeatherForCity_success_returnsMappedAggregatedWeather_andKeepsOrder() {
        // given
        String city = "London";
        WeatherData owm = WeatherData.builder().city(city).temperature(21.0).description("owm").build();
        WeatherData wap = WeatherData.builder().city(city).temperature(23.0).description("wap").build();
        AggregatedWeather agg = AggregatedWeather.builder().city(city).averageTemperature(22.0).build();

        when(openWeatherMapClient.getWeatherByCityName(city)).thenReturn(Mono.just(owm));
        when(weatherApiClient.getWeatherByCityName(city)).thenReturn(Mono.just(wap));
        when(aggregatedWeatherMapper.toAggregatedWeather(anyList())).thenReturn(agg);

        // when / then
        StepVerifier.create(weatherService.getWeatherForCity(city))
                .expectNext(agg)
                .verifyComplete();

        ArgumentCaptor<List<WeatherData>> captor = ArgumentCaptor.forClass(List.class);
        verify(aggregatedWeatherMapper).toAggregatedWeather(captor.capture());
        assertThat(captor.getValue()).containsExactly(owm, wap);
    }

    @Test
    void getWeatherForCity_whenOneClientErrors_propagatesError_andDoesNotCallMapper() {
        // given
        String city = "London";
        when(openWeatherMapClient.getWeatherByCityName(city))
                .thenReturn(Mono.error(new RuntimeException("boom")));
        when(weatherApiClient.getWeatherByCityName(city))
                .thenReturn(Mono.just(WeatherData.builder().city(city).temperature(22.0).build()));

        // when / then
        StepVerifier.create(weatherService.getWeatherForCity(city))
                .expectErrorMessage("boom")
                .verify();

        verifyNoInteractions(aggregatedWeatherMapper);
    }

    @Test
    void getWeatherForCity_whenOneClientIsEmpty_completesEmpty_andDoesNotCallMapper() {
        // given
        String city = "London";
        when(openWeatherMapClient.getWeatherByCityName(city)).thenReturn(Mono.empty());
        when(weatherApiClient.getWeatherByCityName(city))
                .thenReturn(Mono.just(WeatherData.builder().city(city).temperature(22.0).build()));

        // when / then
        StepVerifier.create(weatherService.getWeatherForCity(city))
                .verifyComplete();

        verifyNoInteractions(aggregatedWeatherMapper);
    }
}
