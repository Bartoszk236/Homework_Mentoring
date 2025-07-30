package com.example.RateLimitingAndMonitoring.interceptor;

import com.example.RateLimitingAndMonitoring.config.RateLimitConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RateLimitingInterceptor implements HandlerInterceptor {
    private final RedisTemplate<String, String> redisTemplate;
    private final RateLimitConfig rateLimitConfig;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) {

        String ip = request.getRemoteAddr();
        String key = "rate_limit:" + ip;
        String role = request.getHeader("X-Role");

        long currentTime = System.currentTimeMillis();
        long windowStart = currentTime - rateLimitConfig.getWindowDuration().toMillis();

        redisTemplate.opsForZSet().removeRangeByScore(key, 0, windowStart);

        int maxRequests;
        if (role != null) {
            switch (role) {
                case "unauthorized" -> maxRequests = rateLimitConfig.getLimitForUnauthorizedUser();
                case "authorized" -> maxRequests = rateLimitConfig.getLimitForAuthorizedUser();
                case "premium" -> maxRequests = rateLimitConfig.getLimitForPremiumUser();
                default -> maxRequests = 0;
            }
        } else {
            maxRequests = 0;
        }

        Long currentCount = redisTemplate.opsForZSet().count(key, windowStart, currentTime);

        if (currentCount == null) return false;

        if (currentCount >= maxRequests) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setHeader("X-Rate-Limit-Limit", String.valueOf(maxRequests));
            response.setHeader("X-Rate-Limit-Remaining", "0");
            return false;
        }

        redisTemplate.opsForZSet().add(key, UUID.randomUUID().toString(), currentTime);
        redisTemplate.expire(key, rateLimitConfig.getWindowDuration().toSeconds(), TimeUnit.SECONDS);

        response.setHeader("X-Rate-Limit-Limit", String.valueOf(maxRequests));
        response.setHeader("X-Rate-Limit-Remaining", String.valueOf(maxRequests - currentCount - 1));

        return true;
    }
}
