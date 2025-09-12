package com.MultiTenantUserManagementSystem.dto;

public record UserCreateBulkResponse(
        Integer indexInQueue,
        String status
) {
}
