package com.example.RateLimitingAndMonitoring.service;

import com.example.RateLimitingAndMonitoring.dto.MetricResponse;
import com.example.RateLimitingAndMonitoring.dto.MetricResponseTime;
import com.example.RateLimitingAndMonitoring.entity.ApiMetric;
import com.example.RateLimitingAndMonitoring.repository.ApiMetricRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiMetricService {
    private final ApiMetricRepository apiMetricRepository;

    public void saveMetric(String endpoint, boolean success, long durationInMillis) {
        ApiMetric apiMetric = ApiMetric.builder()
                .endpoint(endpoint)
                .timestamp(LocalDateTime.now())
                .durationInMillis(durationInMillis)
                .success(success)
                .build();
        apiMetricRepository.save(apiMetric);
    }

    public MetricResponse getMetricByEndpointName(String endpointName) {
        String endpointURI = "/" + endpointName;

        if (!apiMetricRepository.existsByEndpoint(endpointURI))
            throw new IllegalArgumentException("Endpoint not found");

        Integer count = apiMetricRepository.countByEndpoint(endpointURI);
        BigDecimal successRate = apiMetricRepository.averageSuccessRateByEndpoint(endpointURI);

        String errorRateInPercent = BigDecimal.ONE.subtract(successRate)
                .multiply(new BigDecimal("100"))
                .round(new MathContext(2, RoundingMode.HALF_EVEN)) + "%";

        List<Long> durations = apiMetricRepository.getDurationsByEndpoint(endpointURI);
        durations.sort(Comparator.naturalOrder());

        BigDecimal listSize = new BigDecimal(durations.size());
        int indexP50 = calculateIndex(listSize, new BigDecimal("0.5"));
        int indexP95 = calculateIndex(listSize, new BigDecimal("0.95"));
        int indexP99 = calculateIndex(listSize, new BigDecimal("0.99"));

        Long averageDuration = apiMetricRepository.getAverageDurationByEndpoint(endpointURI);

        MetricResponseTime metricResponseTime = MetricResponseTime.builder()
                .unit("Milliseconds")
                .average(averageDuration)
                .P50(durations.get(indexP50))
                .P95(durations.get(indexP95))
                .P99(durations.get(indexP99))
                .build();


        return MetricResponse.builder()
                .endpointURI(endpointURI)
                .requestCounter(count)
                .errorRate(errorRateInPercent)
                .responseTime(metricResponseTime)
                .build();
    }

    private int calculateIndex(BigDecimal listSize, BigDecimal percentile) {
        BigDecimal raw = listSize.multiply(percentile);
        BigDecimal roundedUp = raw.setScale(0, RoundingMode.CEILING);
        int index = roundedUp.intValueExact() - 1;
        return Math.max(0, Math.min(index, listSize.intValue() - 1));
    }
}
