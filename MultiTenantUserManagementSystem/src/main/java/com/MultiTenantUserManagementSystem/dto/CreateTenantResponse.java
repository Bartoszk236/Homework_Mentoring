package com.MultiTenantUserManagementSystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateTenantResponse(
        @JsonProperty("name")
        String name,
        @JsonProperty("tenantId")
        String tenantId
) {
}
