package com.MultiTenantUserManagementSystem.interceptor;

import com.MultiTenantUserManagementSystem.utils.RateLimitProperties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.time.Duration;

import static com.MultiTenantUserManagementSystem.utils.StaticsSheet.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RateLimitInterceptorTest {

    @AfterEach
    void tearDown() {
        MDC.clear();
    }

    @Test
    void whenOrderThenReturnTwo() {
        var interceptor = interceptorWith(5, 5, Duration.ofMinutes(1));
        assertThat(interceptor.getOrder()).isEqualTo(2);
    }

    @Test
    void givenThreeTokensWhenPreHandleThenDecreaseTokenValue() {
        //given
        MDC.put(MDC_TENANT_ID_KEY, "xyz123");
        var interceptor = interceptorWith(3, 3, Duration.ofMinutes(1));
        var request = new MockHttpServletRequest("GET", "/users");

        //when / then
        var response1 = new MockHttpServletResponse();
        assertTrue(interceptor.preHandle(request, response1, new Object()));
        assertThat(response1.getHeader(HEADER_RATE_LIMIT_REMAINING)).isEqualTo(String.valueOf(2));

        var response2 = new MockHttpServletResponse();
        assertTrue(interceptor.preHandle(request, response2, new Object()));
        assertThat(response2.getHeader(HEADER_RATE_LIMIT_REMAINING)).isEqualTo(String.valueOf(1));

        var response3 = new MockHttpServletResponse();
        assertTrue(interceptor.preHandle(request, response3, new Object()));
        assertThat(response3.getHeader(HEADER_RATE_LIMIT_REMAINING)).isEqualTo(String.valueOf(0));

        assertThat(response3.getHeader(HEADER_RATE_LIMIT_RESET)).isNotNull();
    }

    @Test
    void givenOneTokenAndCallWhenPreHandleThenIsFalseAndCode429() {
        //given
        MDC.put(MDC_TENANT_ID_KEY, "xyz123");
        var interceptor = interceptorWith(1, 1, Duration.ofMinutes(1));
        var request = new MockHttpServletRequest("GET", "/users");

        //when / then
        var response1 = new MockHttpServletResponse();
        assertTrue(interceptor.preHandle(request, response1, new Object()));

        var response2 = new MockHttpServletResponse();
        assertFalse(interceptor.preHandle(request, response2, new Object()));
        assertThat(response2.getStatus()).isEqualTo(429);
        assertThat(response2.getHeader(HEADER_RETRY_AFTER)).isNotNull();
    }

    @Test
    void givenRequestWithoutTenantIdWhenPreHandleThenUseAddressIP() {
        //given
        MDC.put(MDC_TENANT_ID_KEY, VARIABLE_NOT_APPLICABLE);
        var interceptor = interceptorWith(1, 1, Duration.ofMinutes(1));
        String firstScenerioIPAddress = "203.0.113.10";
        String secondScenerioIPAddress = "203.0.113.11";

        //when / then
        var firstRequest = new MockHttpServletRequest("GET", "/users");
        firstRequest.setRemoteAddr(firstScenerioIPAddress);
        var firstScenerioResponse1 = new MockHttpServletResponse();
        assertTrue(interceptor.preHandle(firstRequest, firstScenerioResponse1, new Object()));

        var firstScenerioResponse2 = new MockHttpServletResponse();
        assertFalse(interceptor.preHandle(firstRequest, firstScenerioResponse2, new Object()));
        assertThat(firstScenerioResponse2.getStatus()).isEqualTo(429);

        var secondScenerioRequest = new MockHttpServletRequest("GET", "/users");
        secondScenerioRequest.setRemoteAddr(secondScenerioIPAddress);
        var secondScenerioResponse = new MockHttpServletResponse();
        assertTrue(interceptor.preHandle(secondScenerioRequest, secondScenerioResponse, new Object()));
    }

    @Test
    void givenBulkRequestWithTenantIdWhenPreHandleThenDecreaseTokenValue() {
        //given
        MDC.put(MDC_TENANT_ID_KEY, "xyz123");
        var interceptor = interceptorWith(10, 10, Duration.ofMinutes(1));

        //when / then
        var bulkRequest = new MockHttpServletRequest("POST", "/users/bulk");
        var response1 = new MockHttpServletResponse();
        assertTrue(interceptor.preHandle(bulkRequest, response1, new Object()));
        assertThat(response1.getHeader(HEADER_RATE_LIMIT_REMAINING)).isEqualTo(String.valueOf(5));

        var response2 = new MockHttpServletResponse();
        assertTrue(interceptor.preHandle(bulkRequest, response2, new Object()));
        assertThat(response2.getHeader(HEADER_RATE_LIMIT_REMAINING)).isEqualTo(String.valueOf(0));

        var response3 = new MockHttpServletResponse();
        assertFalse(interceptor.preHandle(bulkRequest, response3, new Object()));
        assertThat(response3.getStatus()).isEqualTo(429);

        var normalRequest = new MockHttpServletRequest("GET", "/users");
        var response4 = new MockHttpServletResponse();
        assertFalse(interceptor.preHandle(normalRequest, response4, new Object()));
    }

    private RateLimitInterceptor interceptorWith(int capacity, int refill, Duration period) {
        var properties = new RateLimitProperties(capacity, refill, period, 5);
        return new RateLimitInterceptor(properties);
    }
}
