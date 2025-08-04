package com.example.ErrorHandlingAndExceptionManagement.interceptor;

import com.example.ErrorHandlingAndExceptionManagement.model.ErrorInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.MediaType;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ErrorRateLimitingInterceptorTest {
    private final MessageSource messageSource = mock(MessageSource.class);
    private ErrorRateLimitingInterceptor interceptor = new ErrorRateLimitingInterceptor(messageSource);
    private HttpServletRequest request = mock(HttpServletRequest.class);
    private HttpServletResponse response = new MockHttpServletResponse();
    private Map<String, ErrorInfo> map;
    private long brakeInMillis;
    private int maxRetries;
    private final Object handler = new Object();

    @BeforeEach
    void setUp() {
        when(request.getRemoteAddr()).thenReturn("1.2.3.4");
        interceptor.setErrorRateLimitingMap(new ConcurrentHashMap<>());
        maxRetries = 5;
        brakeInMillis = 60_000;
    }

    @Test
    void givenFirstRequestWhenPreHandleThenReturnTrue() throws Exception {
        // when
        boolean result = interceptor.preHandle(request, response, handler);

        // then
        assertTrue(result);
    }

    @Test
    void givenMaxAttemptsWithBrakeBeforeNextRequestWhenPreHandleThenRemoveKeyAndReturnTrue() throws Exception {
        // given
        String key = request.getRemoteAddr();
        ErrorInfo errorInfo = mock(ErrorInfo.class);
        when(errorInfo.getLastUpdateTime())
                .thenReturn(System.currentTimeMillis() - brakeInMillis - 1);
        when(errorInfo.getCounter()).thenReturn(new AtomicInteger(maxRetries));
        ConcurrentHashMap<String, ErrorInfo> map = new ConcurrentHashMap<>();
        map.put(key, errorInfo);
        interceptor.setErrorRateLimitingMap(map);

        // when
        boolean result = interceptor.preHandle(request, response, handler);

        //then
        assertTrue(result);
        assertNull(map.get(key));
    }

    @Test
    void givenMaxAttemptsWithoutBrakeBeforeNextRequestWhenPreHandleThenReturnFalseAndTooManyRequests() throws Exception {
        // given
        String key = request.getRemoteAddr();
        ErrorInfo errorInfo = mock(ErrorInfo.class);
        when(errorInfo.getLastUpdateTime())
                .thenReturn(System.currentTimeMillis());
        when(errorInfo.getCounter()).thenReturn(new AtomicInteger(5));
        ConcurrentHashMap<String, ErrorInfo> map = new ConcurrentHashMap<>();
        map.put(key, errorInfo);
        interceptor.setErrorRateLimitingMap(map);

        String code = "interceptor.too_many_request";
        String message = "Too many requests";
        when(messageSource.getMessage(code, null, Locale.ENGLISH)).thenReturn(message);

        // when
        boolean result = interceptor.preHandle(request, response, handler);

        // then
        assertFalse(result);
        assertEquals(HttpStatus.TOO_MANY_REQUESTS.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON.toString(), response.getContentType());
    }

    @Test
    void givenStatus200WhenAfterCompletionThenMapIsStillEmpty() throws Exception {
        // given
        response.setStatus(200);

        // when
        interceptor.afterCompletion(request, response, new Object(), null);

        // then
        Map<String, ErrorInfo> map = interceptor.getErrorRateLimitingMap();
        assertTrue(map.isEmpty());
    }

    @Test
    void givenStatus500WhenAfterCompletionThenMapIsStillEmpty() throws Exception {
        response.setStatus(500);

        interceptor.afterCompletion(request, response, new Object(), null);

        Map<String, ErrorInfo> map = interceptor.getErrorRateLimitingMap();
        assertTrue(map.isEmpty());
    }

    @Test
    void givenStatus400WhenAfterCompletionThenAddErrorToMap() throws Exception {
        response.setStatus(400);

        interceptor.afterCompletion(request, response, new Object(), null);

        Map<String, ErrorInfo> map = interceptor.getErrorRateLimitingMap();
        assertEquals(1, map.size());
        ErrorInfo info = map.get("1.2.3.4");
        assertNotNull(info);
        assertEquals(1, info.getCounter().get());
        assertTrue(info.getLastUpdateTime() > 0);
    }

    @Test
    void givenStatus400TwoTimesWhenAfterCompletionThenMapHaveRecordWithTwoAttempt() throws Exception {
        // given
        response.setStatus(400);

        // when
        interceptor.afterCompletion(request, response, new Object(), null);
        interceptor.afterCompletion(request, response, new Object(), null);

        // then
        Map<String, ErrorInfo> map = interceptor.getErrorRateLimitingMap();
        ErrorInfo info = map.get("1.2.3.4");
        assertEquals(2, info.getCounter().get());
    }
}
