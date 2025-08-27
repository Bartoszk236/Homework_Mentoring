package com.MultiTenantUserManagementSystem.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StaticsSheet {
    public String MDC_CORRELATION_ID_KEY = "correlationId";
    public String MDC_TENANT_ID_KEY = "tenantId";
    public String HEADER_CORRELATION_ID = "X-Correlation-Id";
    public String HEADER_TENANT_ID = "userId";
    public String FEIGN_LOGGER = "AUDIT_FEIGN_CLIENT";
    public String INTERCEPTOR_LOGGER = "AUDIT_INTERCEPTOR";
    public String STATUS_OPERATION_SUCCESS = "SUCCESS";
    public String STATUS_OPERATION_FAILED = "FAILED";
    public String AUDIT_TIMER = "audit.start";
    public String HEADER_RATE_LIMIT_REMAINING = "X-Rate-Limit-Remaining";
    public String HEADER_RATE_LIMIT_RESET = "X-Rate-Limit-Reset-Seconds";
    public String HEADER_RETRY_AFTER = "X-Retry-After-Seconds";
    public String VARIABLE_NOT_APPLICABLE = "not applicable";
}
