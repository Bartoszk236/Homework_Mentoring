package com.example.WeatherDataAggregationSystem.mapper;

import com.example.WeatherDataAggregationSystem.data.AggregatedWeather;
import com.example.WeatherDataAggregationSystem.data.WeatherData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AggregatedWeatherMapperTest {
    private static Stream<Object[]> roundingCases() {
        return Stream.of(
                new Object[]{List.of(
                        WeatherData.builder().city("X").temperature(10.008).build(),
                        WeatherData.builder().city("X").temperature(10.000).build()
                ), 10.00},

                new Object[]{List.of(
                        WeatherData.builder().city("X").temperature(9.99).build(),
                        WeatherData.builder().city("X").temperature(10.00).build(),
                        WeatherData.builder().city("X").temperature(10.00).build()
                ), 10.00},

                new Object[]{List.of(
                        WeatherData.builder().city("X").temperature(-5.00).build(),
                        WeatherData.builder().city("X").temperature(-5.00).build(),
                        WeatherData.builder().city("X").temperature(-4.99).build()
                ), -5.00}
        );
    }

    private final AggregatedWeatherMapper aggregatedWeatherMapper = new AggregatedWeatherMapper();

    @Test
    void toAggregatedWeather_happyPath_roundsToTwoDecimals_andSetsTimestamp() {
        // given
        List<WeatherData> sources = List.of(
                WeatherData.builder().city("London").temperature(20.12).build(),
                WeatherData.builder().city("London").temperature(21.45).build(),
                WeatherData.builder().city("London").temperature(19.83).build()
        );
        LocalDateTime before = LocalDateTime.now();

        // when
        AggregatedWeather out = aggregatedWeatherMapper.toAggregatedWeather(sources);

        // then
        assertThat(out).isNotNull();
        assertThat(out.getSources()).isSameAs(sources);
        assertThat(out.getCity()).isEqualTo("London");
        assertThat(out.getAverageTemperature()).isEqualTo(20.47);
        assertThat(out.getLastUpdate())
                .isAfterOrEqualTo(before)
                .isBeforeOrEqualTo(LocalDateTime.now());
    }

    @ParameterizedTest(name = "[{index}] avg -> {2}")
    @MethodSource("roundingCases")
    void toAggregatedWeather_rounding_cases(List<WeatherData> sources, double expectedAvg) {
        // when
        AggregatedWeather out = aggregatedWeatherMapper.toAggregatedWeather(sources);

        // then
        assertThat(out.getAverageTemperature()).isEqualTo(expectedAvg);
    }

    @Test
    void toAggregatedWeather_usesCityFromFirstSource_evenIfOthersDiffer() {
        // given
        List<WeatherData> sources = List.of(
                WeatherData.builder().city("FirstCity").temperature(10.0).build(),
                WeatherData.builder().city("SecondCity").temperature(20.0).build()
        );

        // when
        AggregatedWeather out = aggregatedWeatherMapper.toAggregatedWeather(sources);

        // then
        assertThat(out.getCity()).isEqualTo("FirstCity");
    }
}
