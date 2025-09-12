package com.MultiTenantUserManagementSystem.service;

import com.MultiTenantUserManagementSystem.client.ExternalIdentityProviderFeignClient;
import com.MultiTenantUserManagementSystem.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.MultiTenantUserManagementSystem.utils.StaticsSheet.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final ExternalIdentityProviderFeignClient externalIdentityProviderFeignClient;

    public UserResponse createUser(String tenantId, CreateUserRequest request) {
        return externalIdentityProviderFeignClient.createUser(tenantId, request);
    }

    public List<UserResponse> getUsersByTenantId(String tenantId) {
        return externalIdentityProviderFeignClient.getUsersByTenant(tenantId);
    }

    public AuthUserResponse authUser(AuthUserRequest request) {
        return externalIdentityProviderFeignClient.authUser(request);
    }

    public List<UserCreateBulkResponse> createUserBulk(String tenantId, List<CreateUserRequest> requests) {
        if (requests == null || requests.isEmpty()) return List.of();

        List<UserCreateBulkResponse> responses = new ArrayList<>(requests.size());
        for (int i = 0; i < requests.size(); i++) {
            try {
                UserResponse userResponse = externalIdentityProviderFeignClient.createUser(tenantId, requests.get(i));
                String status = (userResponse != null) ? STATUS_OPERATION_SUCCESS : STATUS_OPERATION_FAILED;
                responses.add(new UserCreateBulkResponse(i, status));
            } catch (Exception e) {
                responses.add(new UserCreateBulkResponse(i, STATUS_OPERATION_FAILED));
            }
        }
        return responses;
    }
}
