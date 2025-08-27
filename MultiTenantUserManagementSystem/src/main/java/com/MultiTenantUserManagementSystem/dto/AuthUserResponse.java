package com.MultiTenantUserManagementSystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthUserResponse(
        @JsonProperty("status")
        String status,
        @JsonProperty("token")
        String token
) {
}
