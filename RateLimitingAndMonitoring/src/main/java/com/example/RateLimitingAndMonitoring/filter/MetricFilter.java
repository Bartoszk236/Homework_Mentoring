package com.example.RateLimitingAndMonitoring.filter;

import com.example.RateLimitingAndMonitoring.service.ApiMetricService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(1)
@RequiredArgsConstructor
public class MetricFilter extends OncePerRequestFilter {
    private final ApiMetricService apiMetricService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();
        filterChain.doFilter(request, response);
        String endpoint = request.getRequestURI();
        if (endpoint.contains("h2") || endpoint.contains("/metric")) return;
        apiMetricService.saveMetric(request.getRequestURI(),
                response.getStatus() < 400,
                System.currentTimeMillis() - startTime);
    }
}
