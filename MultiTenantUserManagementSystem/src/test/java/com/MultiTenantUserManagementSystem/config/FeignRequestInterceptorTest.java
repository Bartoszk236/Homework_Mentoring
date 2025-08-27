package com.MultiTenantUserManagementSystem.config;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

import static com.MultiTenantUserManagementSystem.utils.StaticsSheet.*;
import static org.assertj.core.api.Assertions.assertThat;

class FeignRequestInterceptorTest {
    private FeignConfig feignConfig;

    @BeforeEach
    void setUp() {
        feignConfig = new FeignConfig();
        MDC.clear();
    }

    @AfterEach
    void tearDown() {
        MDC.clear();
    }

    @Test
    void givenEmptyTemplateWhenRequestInterceptorThenReturnTemplateWithBaseHeadersGeneratedCorrelationIdIfMissingAndNullTenantId() {
        //given
        var template = new feign.RequestTemplate();

        //when
        feignConfig.requestInterceptor().apply(template);

        //then
        var headers = template.headers();

        assertThat(headers.get("User-Agent")).containsExactly("MultiTenantUserManagementSystem");
        assertThat(headers.get("Accept")).containsExactly("application/json");
        assertThat(headers.get(HEADER_CORRELATION_ID)).isNotEmpty();
        assertThat(headers.get(HEADER_TENANT_ID)).isNull();
    }

    @Test
    void givenTestDataInMDCWhenRequestInterceptorThenReturnTemplateWithDataFromMDC() {
        //given
        String correlationIdValue = "xyz-123";
        String tenantIdValue = "xyz-456";

        MDC.put(MDC_CORRELATION_ID_KEY, correlationIdValue);
        MDC.put(MDC_TENANT_ID_KEY, tenantIdValue);
        var template = new feign.RequestTemplate();

        //when
        feignConfig.requestInterceptor().apply(template);

        //then
        assertThat(template.headers().get(HEADER_CORRELATION_ID)).containsExactly(correlationIdValue);
        assertThat(template.headers().get(HEADER_TENANT_ID)).containsExactly(tenantIdValue);
    }
}
