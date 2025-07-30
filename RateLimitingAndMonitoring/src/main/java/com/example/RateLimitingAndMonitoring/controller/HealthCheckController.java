package com.example.RateLimitingAndMonitoring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.HealthComponent;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/health")
public class HealthCheckController {
    private final HealthEndpoint healthEndpoint;

    @GetMapping
    public ResponseEntity<HealthComponent> health() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(healthEndpoint.health());
    }
}
