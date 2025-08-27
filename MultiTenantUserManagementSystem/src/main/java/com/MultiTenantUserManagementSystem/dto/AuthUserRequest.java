package com.MultiTenantUserManagementSystem.dto;

public record AuthUserRequest(
        String username,
        String password
) {
}
