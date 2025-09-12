package com.MultiTenantUserManagementSystem.dto;

import com.MultiTenantUserManagementSystem.model.Permission;
import com.MultiTenantUserManagementSystem.model.Role;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record UserResponse(
        @JsonProperty("id")
        Long userId,
        @JsonProperty("tenantId")
        String tenantId,
        @JsonProperty("username")
        String username,
        @JsonProperty("phoneNumber")
        String phoneNumber,
        @JsonProperty("role")
        Role role,
        @JsonProperty("permissions")
        List<Permission> permissions
) {
}
