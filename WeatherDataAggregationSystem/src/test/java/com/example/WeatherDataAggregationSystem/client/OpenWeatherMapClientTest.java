package com.example.WeatherDataAggregationSystem.client;

import com.example.WeatherDataAggregationSystem.data.WeatherData;
import com.example.WeatherDataAggregationSystem.exception.ExternalApiException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import mockwebserver3.*;
import okhttp3.Headers;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OpenWeatherMapClientTest {
    private static final String CACHE_NAME = "weatherData";
    private static final String CITY = "London";
    private static final String CACHE_KEY = CITY + "apiOpenWeatherMap";
    static MockWebServer mockWebServer;
    private CacheManager cacheManager;
    private CircuitBreaker circuitBreaker;
    private OpenWeatherMapClient client;
    private final WeatherData expectedWeatherData = WeatherData.builder()
            .city("London")
            .temperature(23.48)
            .description("overcast clouds")
            .source("OpenWeatherMap")
            .timestamp(LocalDateTime.now())
            .build();

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        String baseUrl = mockWebServer.url("/data").toString();
        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();

        cacheManager = new ConcurrentMapCacheManager(CACHE_NAME);
        circuitBreaker = testCircuitBreaker();

        client = new OpenWeatherMapClient(
                webClient,
                "api-key",
                circuitBreaker,
                cacheManager
        );
    }

    @AfterEach
    void tearDown() {
        mockWebServer.close();
    }

    @Test
    void getWeatherByCityName_successfulResponse_returnsWeatherDataAndCache() {
        //given
        mockWebServer.enqueue(
                new MockResponse(
                        HttpStatus.OK.value(),
                        Headers.of(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE),
                        ExternalApiResponses.getJsonResponseOpenWeatherMap())
        );

        //when
        WeatherData resultWeatherData = client.getWeatherByCityName(CITY).block();

        //then
        checkObjectFields(resultWeatherData);

        assertNotNull(cacheManager.getCache(CACHE_NAME)
                .get(CACHE_KEY, WeatherData.class));
    }

    @Test
    void getWeatherByCityName_errorResponse_returnsWeatherDataFromCache() {
        //given
        mockWebServer.close();

        cacheManager.getCache(CACHE_NAME).put(CITY + "apiOpenWeatherMap", expectedWeatherData);

        //when
        Mono<WeatherData> result = client.getWeatherByCityName(CITY);
        WeatherData resultWeatherData = result.block();

        //then
        checkObjectFields(resultWeatherData);
    }

    @Test
    void getWeatherByCityName_errorResponse_emptyCache_throwExternalApiException() {
        //given
        mockWebServer.close();

        //when / then
        assertThrows(ExternalApiException.class, () -> client.getWeatherByCityName(CITY).block());
    }

    @Test
    void getWeatherByCityName_timeout_returnsWeatherDataFromCache() {
        //given
        mockWebServer.setDispatcher(new Dispatcher() {
            @NotNull
            @Override
            public MockResponse dispatch(@NotNull RecordedRequest recordedRequest) throws InterruptedException {
                Thread.sleep(11_000);
                return new MockResponse(
                        HttpStatus.GATEWAY_TIMEOUT.value(),
                        Headers.of(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE),
                        "");
            }
        });

        cacheManager.getCache(CACHE_NAME).put(CACHE_KEY, expectedWeatherData);

        //when
        Mono<WeatherData> result = client.getWeatherByCityName(CITY);
        WeatherData resultWeatherData = result.block();

        //then
        checkObjectFields(resultWeatherData);
    }

    @Test
    void getWeatherByCityName_timeout_emptyCache_throwExternalApiException() {
        //given
        mockWebServer.setDispatcher(new Dispatcher() {
            @NotNull
            @Override
            public MockResponse dispatch(@NotNull RecordedRequest recordedRequest) throws InterruptedException {
                Thread.sleep(11_000);
                return new MockResponse(
                        HttpStatus.GATEWAY_TIMEOUT.value(),
                        Headers.of(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE),
                        "");
            }
        });

        //when / then
        assertThrows(ExternalApiException.class, () -> client.getWeatherByCityName(CITY).block());
    }

    @Test
    void getWeatherByCityName_openCircuitBreaker_emptyCache_throwExternalApiException() {
        //given
        mockWebServer.setDispatcher(new Dispatcher() {
            @NotNull
            @Override
            public MockResponse dispatch(@NotNull RecordedRequest req) {
                return new MockResponse.Builder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .headers(Headers.of(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                        .body("")
                        .build();
            }
        });

        //when / then
        StepVerifier.create(client.getWeatherByCityName(CITY))
                .expectErrorSatisfies(ex ->
                        assertInstanceOf(ExternalApiException.class, ex))
                .verify();

        assertEquals(CircuitBreaker.State.OPEN, circuitBreaker.getState());
    }

    @Test
    void getWeatherByCityName_openCircuitBreaker_returnsWeatherDataFromCache() {
        //given
        mockWebServer.setDispatcher(new Dispatcher() {
            @NotNull
            @Override
            public MockResponse dispatch(@NotNull RecordedRequest req) {
                return new MockResponse.Builder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .headers(Headers.of(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                        .body("")
                        .build();
            }
        });

        // with empty cache
        StepVerifier.create(client.getWeatherByCityName(CITY))
                .expectError()
                .verify();
        assertEquals(CircuitBreaker.State.OPEN, circuitBreaker.getState());

        // with cache and open circuitBreaker
        cacheManager.getCache(CACHE_NAME).put(CACHE_KEY, expectedWeatherData);
        WeatherData resultWeatherData = client.getWeatherByCityName(CITY).block();

        checkObjectFields(resultWeatherData);
    }

    @Test
    void getWeatherByCityName_halfOpenCircuitBreaker_afterSuccessClosesCircuit() throws InterruptedException {
        //given
        for (int i = 0; i < 4; i++) {
            mockWebServer.enqueue(new MockResponse.Builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .headers(Headers.of(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                    .body("")
                    .build());
        }

        //when
        StepVerifier.create(client.getWeatherByCityName(CITY))
                .expectError()
                .verify();

        assertEquals(CircuitBreaker.State.OPEN, circuitBreaker.getState());

        Thread.sleep(250);

        mockWebServer.enqueue(new MockResponse.Builder()
                .code(HttpStatus.OK.value())
                .headers(Headers.of(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .body(ExternalApiResponses.getJsonResponseOpenWeatherMap())
                .build());

        WeatherData resultWeatherData = client.getWeatherByCityName(CITY).block();

        // then
        assertEquals(CircuitBreaker.State.CLOSED, circuitBreaker.getState());
    }

    private CircuitBreaker testCircuitBreaker() {
        CircuitBreakerConfig cbConfig = CircuitBreakerConfig.custom()
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .slidingWindowSize(2)
                .minimumNumberOfCalls(2)
                .failureRateThreshold(50f)
                .waitDurationInOpenState(Duration.ofMillis(200))
                .permittedNumberOfCallsInHalfOpenState(1)
                .recordExceptions(
                        WebClientException.class,
                        TimeoutException.class
                )
                .build();
        return CircuitBreaker.of("cb-test", cbConfig);
    }

    private void checkObjectFields(WeatherData resultWeatherData) {
        assertNotNull(resultWeatherData);
        assertEquals("London", resultWeatherData.getCity());
        assertEquals(23.48, resultWeatherData.getTemperature());
        assertEquals("overcast clouds", resultWeatherData.getDescription());
        assertEquals("OpenWeatherMap", resultWeatherData.getSource());
        assertNotNull(resultWeatherData.getTimestamp());
    }
}
