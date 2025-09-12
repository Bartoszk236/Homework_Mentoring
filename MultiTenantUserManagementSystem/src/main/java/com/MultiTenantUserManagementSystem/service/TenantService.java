package com.MultiTenantUserManagementSystem.service;

import com.MultiTenantUserManagementSystem.client.ExternalIdentityProviderFeignClient;
import com.MultiTenantUserManagementSystem.dto.CreateTenantRequest;
import com.MultiTenantUserManagementSystem.dto.CreateTenantResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TenantService {
    private final ExternalIdentityProviderFeignClient externalIdentityProviderFeignClient;

    public CreateTenantResponse createTenant(CreateTenantRequest request) {
        return externalIdentityProviderFeignClient.createTenant(request);
    }

    public void deleteTenant(String tenantId) {
        externalIdentityProviderFeignClient.deleteTenant(tenantId);
    }
}
