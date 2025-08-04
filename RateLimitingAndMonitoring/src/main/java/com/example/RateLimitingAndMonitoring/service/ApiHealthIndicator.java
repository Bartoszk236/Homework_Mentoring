package com.example.RateLimitingAndMonitoring.service;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
public class ApiHealthIndicator implements HealthIndicator {
    private final RestClient client;

    public ApiHealthIndicator(RestClient.Builder builder) {
        this.client = builder.baseUrl("http://localhost:8080").build();
    }

    @Override
    public Health health() {
        Health.Builder builder = new Health.Builder();
        try {
            var response = client
                    .get()
                    .uri("/test")
                    .retrieve()
                    .toEntity(String.class);

            HttpStatusCode status = response.getStatusCode();
            int statusCode = status.value();
            String body = response.getBody();

            if (statusCode >= 200 && statusCode < 300 && body != null && !body.isEmpty()) {
                builder.up()
                        .withDetail("external", "/test reachable")
                        .withDetail("status", statusCode);
            } else {
                builder.down()
                        .withDetail("external", "/test returned non-2xx or empty body")
                        .withDetail("status", statusCode);
            }
        } catch (RestClientException ex) {
            builder.down(ex)
                    .withDetail("external", "unreachable");
        }
        builder.withDetail("api.version", "1.0.0");

        return builder.build();
    }
}
