package com.MultiTenantUserManagementSystem.interceptor;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

import static com.MultiTenantUserManagementSystem.utils.StaticsSheet.*;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class AuditInterceptorTest {
    private AuditInterceptor auditInterceptor;
    private Logger auditLogger;
    private ListAppender<ILoggingEvent> appender;

    @BeforeEach
    void setUp() {
        auditInterceptor = new AuditInterceptor();
        auditLogger = (Logger) LoggerFactory.getLogger(INTERCEPTOR_LOGGER);
        appender = new ListAppender<>();
        appender.start();
        auditLogger.addAppender(appender);
        MDC.clear();
    }

    @AfterEach
    void tearDown() {
        auditLogger.detachAppender(appender);
        MDC.clear();
    }

    @Test
    void whenOrderThenReturnOne() {
        assertThat(auditInterceptor.getOrder()).isEqualTo(1);
    }

    @Test
    void givenRequestWithTenantIdInURLAndHeaderCorrelationIdAndAttribute() {
        //given
        var request = new MockHttpServletRequest("GET", "/tenant/xyz123/users");
        var response = new MockHttpServletResponse();
        request.addHeader(HEADER_CORRELATION_ID, "yzx321");
        request.setAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, Map.of("tenantId", "xyz123"));

        //when
        boolean ok = auditInterceptor.preHandle(request, response, new Object());

        //then
        assertThat(ok).isTrue();
        assertThat(MDC.get(MDC_TENANT_ID_KEY)).isEqualTo("xyz123");
        assertThat(MDC.get(MDC_CORRELATION_ID_KEY)).isEqualTo("yzx321");
        assertThat(request.getAttribute(AUDIT_TIMER)).isInstanceOf(Long.class);
    }

    @Test
    void givenPathWithoutTenantIdInURLAndHeaderCorrelationIdWhenPreHandleThenGenerateCorrelationIdAndSetTenantIdToStatic() {
        //given
        var request = new MockHttpServletRequest("GET", "/users");
        var response = new MockHttpServletResponse();

        //when
        auditInterceptor.preHandle(request, response, new Object());

        //then
        assertThat(MDC.get(MDC_CORRELATION_ID_KEY)).isNotBlank();
        assertThat(MDC.get(MDC_TENANT_ID_KEY)).isEqualTo(VARIABLE_NOT_APPLICABLE);
        assertThat(request.getAttribute(AUDIT_TIMER)).isInstanceOf(Long.class);
    }

    @Test
    void given201ResponseWhenAfterCompletionThenLogDataAndClearMDC() {
        //given
        var request = new MockHttpServletRequest("GET", "/tenant/xyz123/users");
        var response = new MockHttpServletResponse();
        MDC.put(MDC_CORRELATION_ID_KEY, "yzx321");
        MDC.put(MDC_TENANT_ID_KEY, "xyz123");
        request.setAttribute(AUDIT_TIMER, System.currentTimeMillis());
        response.setStatus(201);

        //when
        auditInterceptor.afterCompletion(request, response, new Object(), null);

        //then
        assertThat(appender.list).hasSize(1);
        String message = appender.list.getFirst().getFormattedMessage();
        assertThat(message)
                .contains("request=GET")
                .contains("path=/tenant/xyz123/users")
                .contains("status=201")
                .contains("tenantId=xyz123")
                .contains("correlationId=yzx321")
                .contains("outcome=" + STATUS_OPERATION_SUCCESS)
                .contains("durationMs=");

        assertThat(MDC.get(MDC_CORRELATION_ID_KEY)).isNull();
        assertThat(MDC.get(MDC_TENANT_ID_KEY)).isNull();
    }

    @Test
    void givenExceptionInResponseWhenAfterCompletionThenLogStatusFailed() {
        //given
        var request = new MockHttpServletRequest("GET", "/tenant/xyz123/users");
        var response = new MockHttpServletResponse();
        MDC.put(MDC_CORRELATION_ID_KEY, "yzx321");
        MDC.put(MDC_TENANT_ID_KEY, "xyz123");
        request.setAttribute(AUDIT_TIMER, System.currentTimeMillis());

        //when
        auditInterceptor.afterCompletion(request, response, new Object(), new RuntimeException("exception"));

        //then
        String message = appender.list.getFirst().getFormattedMessage();
        assertThat(message).contains("outcome=" + STATUS_OPERATION_FAILED);
    }

    @Test
    void givenResponseWithCode404WhenAfterCompletionThenLogStatusFailed() {
        //given
        var request = new MockHttpServletRequest("GET", "/tenant/xyz123/users");
        var response = new MockHttpServletResponse();
        MDC.put(MDC_CORRELATION_ID_KEY, "yzx321");
        MDC.put(MDC_TENANT_ID_KEY, "xyz123");
        request.setAttribute(AUDIT_TIMER, System.currentTimeMillis());
        response.setStatus(404);

        //when
        auditInterceptor.afterCompletion(request, response, new Object(), null);
        String message = appender.list.getFirst().getFormattedMessage();
        assertThat(message)
                .contains("outcome=" + STATUS_OPERATION_FAILED)
                .contains("status=404");
    }

    @Test
    void givenResponseWithoutTimerWhenAfterCompletionThenLogDurationMinusOne() {
        //given
        var request = new MockHttpServletRequest("GET", "/tenant/xyz123/users");
        var response = new MockHttpServletResponse();
        MDC.put(MDC_CORRELATION_ID_KEY, "yzx321");
        MDC.put(MDC_TENANT_ID_KEY, "xyz123");

        //when
        auditInterceptor.afterCompletion(request, response, new Object(), null);
        String message = appender.list.getFirst().getFormattedMessage();
        assertThat(message).contains("durationMs=-1");
    }
}
