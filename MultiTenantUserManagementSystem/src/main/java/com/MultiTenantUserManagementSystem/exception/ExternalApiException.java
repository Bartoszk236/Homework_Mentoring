package com.MultiTenantUserManagementSystem.exception;

import lombok.Getter;

@Getter
public class ExternalApiException extends RuntimeException {
    private final int ExternalApiResponseCode;

    public ExternalApiException(String message, int ExternalApiResponseCode) {
        super(message);
        this.ExternalApiResponseCode = ExternalApiResponseCode;
    }
}
