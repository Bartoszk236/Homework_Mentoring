package com.example.RateLimitingAndMonitoring.controller;

import com.example.RateLimitingAndMonitoring.dto.MetricResponse;
import com.example.RateLimitingAndMonitoring.service.ApiMetricService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/metric")
@RequiredArgsConstructor
public class MetricController {
    private final ApiMetricService apiMetricService;

    @GetMapping("/{endpointName}")
    public ResponseEntity<MetricResponse> getMetric(
            @PathVariable String endpointName) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(apiMetricService.getMetricByEndpointName(endpointName));
    }
}
