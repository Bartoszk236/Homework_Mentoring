package com.MultiTenantUserManagementSystem.dto;

import com.MultiTenantUserManagementSystem.model.Role;
import com.MultiTenantUserManagementSystem.model.Permission;

import java.util.List;

public record CreateUserRequest(
        String username,
        String password,
        String phoneNumber,
        Role role,
        List<Permission> permissions
) {
}
