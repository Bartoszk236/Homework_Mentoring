package com.example.RateLimitingAndMonitoring.filter;

import com.example.RateLimitingAndMonitoring.service.ApiMetricService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class MetricFilterTest {
    private final ApiMetricService apiMetricService = mock(ApiMetricService.class);
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final FilterChain filterChain = mock(FilterChain.class);
    private final MetricFilter metricFilter = new MetricFilter(apiMetricService);

    @Test
    void givenH2URIWhenDoFilterInternalThenNotSaveRecord() throws ServletException, IOException {
        //given
        when(request.getRequestURI()).thenReturn("/h2-console");
        when(response.getStatus()).thenReturn(200);

        //when
        metricFilter.doFilter(request, response, filterChain);

        //then
        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(apiMetricService);
    }

    @Test
    void givenMetricURIWhenDoFilterInternalThenNotSaveRecord() throws ServletException, IOException {
        //given
        when(request.getRequestURI()).thenReturn("/metric");
        when(response.getStatus()).thenReturn(200);

        //when
        metricFilter.doFilter(request, response, filterChain);

        //then
        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(apiMetricService);
    }

    @Test
    void givenValidRequestWithSuccessStatusWhenDoFilterInternalThenSaveRecord() throws ServletException, IOException {
        //given
        String uri = "/api/data";
        when(request.getRequestURI()).thenReturn(uri);
        when(response.getStatus()).thenReturn(200);

        //when
        metricFilter.doFilter(request, response, filterChain);

        //then
        verify(filterChain).doFilter(request, response);
        ArgumentCaptor<String> uriCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Boolean> successCaptor = ArgumentCaptor.forClass(Boolean.class);
        ArgumentCaptor<Long> durationCaptor = ArgumentCaptor.forClass(Long.class);
        verify(apiMetricService).saveMetric(uriCaptor.capture(), successCaptor.capture(), durationCaptor.capture());

        assertEquals(uri, uriCaptor.getValue());
        assertTrue(successCaptor.getValue());
        assertTrue(durationCaptor.getValue() >= 0);
    }

    @Test
    void givenValidRequestWithErrorStatusWhenDoFilterInternalThenSaveRecord() throws ServletException, IOException {
        //given
        String uri = "/api/data";
        when(request.getRequestURI()).thenReturn(uri);
        when(response.getStatus()).thenReturn(500);

        //when
        metricFilter.doFilter(request, response, filterChain);

        //then
        verify(filterChain).doFilter(request, response);
        ArgumentCaptor<String> uriCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Boolean> successCaptor = ArgumentCaptor.forClass(Boolean.class);
        ArgumentCaptor<Long> durationCaptor = ArgumentCaptor.forClass(Long.class);
        verify(apiMetricService).saveMetric(uriCaptor.capture(), successCaptor.capture(), durationCaptor.capture());

        assertEquals(uri, uriCaptor.getValue());
        assertFalse(successCaptor.getValue());
        assertTrue(durationCaptor.getValue() >= 0);
    }
}
