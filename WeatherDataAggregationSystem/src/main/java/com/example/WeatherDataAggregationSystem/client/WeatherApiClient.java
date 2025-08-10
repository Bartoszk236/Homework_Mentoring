package com.example.WeatherDataAggregationSystem.client;

import com.example.WeatherDataAggregationSystem.dto.WeatherApiResponse;
import com.example.WeatherDataAggregationSystem.data.WeatherData;
import com.example.WeatherDataAggregationSystem.exception.ExternalApiException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.Exceptions;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

@Component
@Slf4j
public class WeatherApiClient {
    private final WebClient webClient;
    private final String apiKey;
    private final CircuitBreaker circuitBreaker;
    private final Cache cache;

    public WeatherApiClient(
            @Qualifier("weatherApi") WebClient webClient,
            @Value("${weather.api.key}") String apiKey,
            CircuitBreaker circuitBreaker,
            CacheManager cacheManager) {
        this.webClient = webClient;
        this.apiKey = apiKey;
        this.circuitBreaker = circuitBreaker;
        this.cache = cacheManager.getCache("weatherData");
    }

    public Mono<WeatherData> getWeatherByCityName(String city) {
        String cacheKey = city + "weatherApi";
        long start = System.currentTimeMillis();
        Mono<WeatherData> remote = webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("q", city)
                        .queryParam("key", apiKey)
                        .build()
                )
                .retrieve()
                .bodyToMono(WeatherApiResponse.class)
                .map(WeatherApiResponse::toWeatherData)
                .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
                .retryWhen(
                        Retry.backoff(3, Duration.ofMillis(200))
                                .maxBackoff(Duration.ofSeconds(2))
                                .jitter(0.5)
                                .filter(error -> error instanceof WebClientException)
                                .doAfterRetry(retrySignal -> {
                                    log.warn("WeatherApiClient::getWeatherByCityName | retry #{} because: {}",
                                            retrySignal.totalRetriesInARow() + 1,
                                            retrySignal.failure().getMessage());
                                })
                )
                .timeout(Duration.ofSeconds(10))
                .doOnSuccess(weatherData -> {
                    long end = System.currentTimeMillis();
                    long duration = end - start;
                    log.info("WeatherApiClient::getWeatherByCityName | " +
                            "Duration: {} ms | " +
                            "Successful fetching data for city: {}", duration, city);
                })
                .doOnNext(data -> cache.put(cacheKey, data));

        Mono<WeatherData> fromCache = Mono.defer(() -> {
            WeatherData cd = cache.get(cacheKey, WeatherData.class);
            return cd != null
                    ? Mono.just(cd)
                    : Mono.empty();
        });

        return remote
                .onErrorResume(err ->
                                Exceptions.isRetryExhausted(err) ||
                                        err instanceof WebClientException ||
                                        err instanceof TimeoutException ||
                                        err instanceof CallNotPermittedException,
                        err -> {
                            log.warn("WeatherApiClient::getWeatherByCityName | " +
                                            "error fetching {}: {}, falling back to cache",
                                    city, err.getMessage());
                            return fromCache
                                    .switchIfEmpty(Mono.error(
                                            new ExternalApiException("WeatherApiClient::getWeatherByCityName | " +
                                                    "No cached data for city: " + city)
                                    ));
                        }
                );
    }
}
