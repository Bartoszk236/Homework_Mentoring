package com.MultiTenantUserManagementSystem.interceptor;

import com.MultiTenantUserManagementSystem.utils.RateLimitProperties;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static com.MultiTenantUserManagementSystem.utils.StaticsSheet.*;

@Component
@RequiredArgsConstructor
public class RateLimitInterceptor implements HandlerInterceptor, Ordered {
    private final RateLimitProperties rateLimitProperties;
    private final ConcurrentHashMap<String, Bucket> buckets = new ConcurrentHashMap<>();

    @Override
    public int getOrder() {
        return 2;
    }

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        String tenantId = MDC.get(MDC_TENANT_ID_KEY);
        if (tenantId.equalsIgnoreCase(VARIABLE_NOT_APPLICABLE)) tenantId = request.getRemoteAddr();
        String key = tenantId;

        Bucket bucket = buckets.computeIfAbsent(key, this::newBucket);
        int cost = isBulk(request) ? rateLimitProperties.bulkCost() : 1;
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(cost);

        if (probe.isConsumed()) {
            response.setHeader(HEADER_RATE_LIMIT_REMAINING, String.valueOf(probe.getRemainingTokens()));
            if (bucket.getAvailableTokens() == 0) {
                long resetSec = TimeUnit.NANOSECONDS.toSeconds(probe.getNanosToWaitForRefill());
                response.setHeader(HEADER_RATE_LIMIT_RESET, String.valueOf(resetSec));
            }
            return true;
        }
        long waitSec = TimeUnit.NANOSECONDS.toSeconds(probe.getNanosToWaitForRefill()) + 1;
        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        response.setHeader(HEADER_RETRY_AFTER, String.valueOf(waitSec));
        return false;
    }

    private boolean isBulk(@NonNull HttpServletRequest request) {
        return request.getRequestURI().contains("/bulk") && request.getMethod().equalsIgnoreCase("POST");
    }

    private Bucket newBucket(String key) {
        Bandwidth limit = Bandwidth.builder()
                .capacity(rateLimitProperties.capacity())
                .refillGreedy(rateLimitProperties.refillTokens(), rateLimitProperties.refillPeriod())
                .build();
        return Bucket.builder().addLimit(limit).build();
    }
}
