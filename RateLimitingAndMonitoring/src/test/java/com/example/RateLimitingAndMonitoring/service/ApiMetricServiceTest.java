package com.example.RateLimitingAndMonitoring.service;

import com.example.RateLimitingAndMonitoring.dto.MetricResponse;
import com.example.RateLimitingAndMonitoring.dto.MetricResponseTime;
import com.example.RateLimitingAndMonitoring.repository.ApiMetricRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApiMetricServiceTest {
    @Mock
    private ApiMetricRepository apiMetricRepository;
    @InjectMocks
    private ApiMetricService apiMetricService;

    @Test
    void givenInvalidEndpointWhenGetMetricByEndpointNameThenThrowException() {
        //given
        String name = "test";

        //when/then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> apiMetricService.getMetricByEndpointName(name)
        );
        assertEquals("Endpoint not found", exception.getMessage());
    }

    @Test
    void givenValidEndpointWhenGetMetricByEndpointNameThenReturnMetric() {
        //given
        String name = "test";
        String uri = "/" + name;
        int count = 10;
        BigDecimal successRate = new BigDecimal("0.75");
        List<Long> durations = Arrays.asList(123L,  456L, 789L);
        long avrDuration = 1000L;

        when(apiMetricRepository.existsByEndpoint(uri)).thenReturn(true);
        when(apiMetricRepository.countByEndpoint(uri)).thenReturn(count);
        when(apiMetricRepository.averageSuccessRateByEndpoint(uri)).thenReturn(successRate);
        when(apiMetricRepository.getDurationsByEndpoint(uri)).thenReturn(durations);
        when(apiMetricRepository.getAverageDurationByEndpoint(uri)).thenReturn(avrDuration);

        //when
        MetricResponse metricResponse = apiMetricService.getMetricByEndpointName(name);

        //then
        assertEquals(uri, metricResponse.endpointURI());
        assertEquals(count, metricResponse.requestCounter());
        assertEquals("25%", metricResponse.errorRate());

        MetricResponseTime metricResponseTime = metricResponse.responseTime();
        assertEquals("Milliseconds", metricResponseTime.unit());
        assertEquals(avrDuration, metricResponseTime.average());
        assertEquals(456L, metricResponseTime.P50());
        assertEquals(789L, metricResponseTime.P95());
        assertEquals(789L, metricResponseTime.P99());
    }
}
