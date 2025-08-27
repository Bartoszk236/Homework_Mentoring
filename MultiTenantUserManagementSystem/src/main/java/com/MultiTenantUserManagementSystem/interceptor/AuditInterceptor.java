package com.MultiTenantUserManagementSystem.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import static com.MultiTenantUserManagementSystem.utils.StaticsSheet.*;

@Component
public class AuditInterceptor implements HandlerInterceptor, Ordered {
    private static final Logger AUDIT = LoggerFactory.getLogger(INTERCEPTOR_LOGGER);

    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response,
                             @NonNull Object handler) {
        String correlationId = request.getHeader(HEADER_CORRELATION_ID);
        if (correlationId == null || correlationId.isBlank()) correlationId = UUID.randomUUID().toString();
        MDC.put(MDC_CORRELATION_ID_KEY, correlationId);

        @SuppressWarnings("unchecked")
        Map<String, String> pathVariables = (Map<String, String>)
                request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String tenantId = pathVariables != null ? pathVariables.get("tenantId") : null;
        if (tenantId == null) tenantId = VARIABLE_NOT_APPLICABLE;

        MDC.put(MDC_TENANT_ID_KEY, tenantId);

        request.setAttribute(AUDIT_TIMER, System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                @NonNull Object handler, Exception ex) {
        Long startTime = (Long) request.getAttribute(AUDIT_TIMER);
        long duration = (startTime != null) ? System.currentTimeMillis() - startTime : -1L;
        String outcome = (ex == null && response.getStatus() < 400)
                ? STATUS_OPERATION_SUCCESS : STATUS_OPERATION_FAILED;

        Map<String, Object> map = Map.of(
                "timestamp", Instant.now(),
                "request", request.getMethod(),
                "path", request.getRequestURI(),
                "status", response.getStatus(),
                "tenantId", MDC.get(MDC_TENANT_ID_KEY),
                "correlationId", MDC.get(MDC_CORRELATION_ID_KEY),
                "durationMs", duration,
                "outcome", outcome
        );
        AUDIT.info(map.toString());
        MDC.remove(MDC_TENANT_ID_KEY);
        MDC.remove(MDC_CORRELATION_ID_KEY);
    }
}
