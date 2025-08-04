package com.example.RateLimitingAndMonitoring.interceptor;

import com.example.RateLimitingAndMonitoring.config.RateLimitConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RateLimitingInterceptorTest {
    @Mock
    private RedisTemplate<String, String> redisTemplate;
    @Mock
    private ZSetOperations<String, String> zSetOperations;
    @Mock
    private RateLimitConfig rateLimitConfig;

    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;
    @Mock
    private Object handler;

    private RateLimitingInterceptor rateLimitingInterceptor;

    private final String ip = "192.0.2.1";
    private final String key = "rate_limit:" + ip;

    @BeforeEach
    void setUp() {
        when(redisTemplate.opsForZSet()).thenReturn(zSetOperations);
        when(rateLimitConfig.getWindowDuration()).thenReturn(Duration.ofSeconds(60));
        when(rateLimitConfig.getLimitForUnauthorizedUser()).thenReturn(5);
        when(rateLimitConfig.getLimitForAuthorizedUser()).thenReturn(10);
        when(rateLimitConfig.getLimitForPremiumUser()).thenReturn(20);

        rateLimitingInterceptor = new RateLimitingInterceptor(redisTemplate, rateLimitConfig);
        when(httpServletRequest.getRemoteAddr()).thenReturn(ip);
    }

    @Test
    void givenValidRequestWhenPreHandleThenReturnTrue() {
        // given
        when(httpServletRequest.getHeader("X-Role")).thenReturn("unauthorized");
        when(zSetOperations.count(eq(key), anyDouble(), anyDouble())).thenReturn(0L);

        //when
        boolean result = rateLimitingInterceptor.preHandle(httpServletRequest, httpServletResponse, handler);

        //then
        assertTrue(result);
    }

    @Test
    void givenRequestWithOverLimitWhenPreHandleThenReturnFalse() {
        //given
        when(httpServletRequest.getHeader("X-Role")).thenReturn("authorized");
        when(zSetOperations.count(eq(key), anyDouble(), anyDouble())).thenReturn(10L);

        //when
        boolean result = rateLimitingInterceptor.preHandle(httpServletRequest, httpServletResponse, handler);

        //then
        assertFalse(result);
    }

    @Test
    void givenRequestWithNullAttemptsWhenPreHandleThenReturnFalse() {
        //given
        when(httpServletRequest.getHeader("X-Role")).thenReturn("premium");
        when(zSetOperations.count(eq(key), anyDouble(), anyDouble())).thenReturn(null);

        //when
        boolean result = rateLimitingInterceptor.preHandle(httpServletRequest, httpServletResponse, handler);

        //then
        assertFalse(result);
    }

    @Test
    void givenRequestWithNullRoleWhenPreHandleThenReturnFalse() {
        //given
        when(httpServletRequest.getHeader("X-Role")).thenReturn(null);
        when(zSetOperations.count(eq(key), anyDouble(), anyDouble())).thenReturn(0L);

        //when
        boolean result = rateLimitingInterceptor.preHandle(httpServletRequest, httpServletResponse, handler);

        //then
        assertFalse(result);
    }
}
