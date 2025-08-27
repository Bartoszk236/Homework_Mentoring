package com.MultiTenantUserManagementSystem.config;

import com.MultiTenantUserManagementSystem.decoder.CustomErrorDecoder;
import feign.*;
import feign.codec.ErrorDecoder;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import static com.MultiTenantUserManagementSystem.utils.StaticsSheet.*;

@Configuration
public class FeignConfig {

    @Bean
    public Retryer retryer() {
        return new Retryer.Default(1000, 2000, 3);
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("User-Agent", "MultiTenantUserManagementSystem");
            requestTemplate.header("Accept", MediaType.APPLICATION_JSON_VALUE);

            String correlationId = MDC.get(MDC_CORRELATION_ID_KEY);
            if (correlationId == null) correlationId = UUID.randomUUID().toString();
            requestTemplate.header(HEADER_CORRELATION_ID, correlationId);

            String tenantId = MDC.get(MDC_TENANT_ID_KEY);
            if (tenantId != null) requestTemplate.header(HEADER_TENANT_ID, tenantId);
        };
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    Logger feignAuditLogger() {
        var AUDIT = LoggerFactory.getLogger(FEIGN_LOGGER);
        return new Logger() {

            @Override
            protected void log(String s, String s1, Object... objects) {
            }

            @Override
            protected void logRequest(String configKey, Level logLevel, Request request) {
                Map<String, Object> data = Map.of(
                        "timestamp", Instant.now(),
                        "direction", "OUT",
                        "method", request.httpMethod().name(),
                        "url", request.url(),
                        "tenantId", MDC.get(MDC_TENANT_ID_KEY),
                        "correlationId", MDC.get(MDC_CORRELATION_ID_KEY)
                );
                AUDIT.info(data.toString());
            }

            @Override
            protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime) {
                String outcome = response.status() < 400 ? STATUS_OPERATION_SUCCESS : STATUS_OPERATION_FAILED;
                Map<String, Object> data = Map.of(
                        "timestamp", Instant.now(),
                        "direction", "IN",
                        "status", response.status(),
                        "durationMs", elapsedTime,
                        "tenantId", MDC.get(MDC_TENANT_ID_KEY),
                        "correlationId", MDC.get(MDC_CORRELATION_ID_KEY),
                        "outcome", outcome
                );
                AUDIT.info(data.toString());
                return response;
            }
        };
    }
}
