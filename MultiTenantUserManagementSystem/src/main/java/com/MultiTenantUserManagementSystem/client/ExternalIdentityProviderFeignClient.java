package com.MultiTenantUserManagementSystem.client;

import com.MultiTenantUserManagementSystem.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        name = "external-idp-service",
        url = "${external.idp.service.url}",
        configuration = FeignClientProperties.FeignClientConfiguration.class
)
public interface ExternalIdentityProviderFeignClient {

    @PostMapping("/tenant/create")
    CreateTenantResponse createTenant(@RequestBody CreateTenantRequest request);

    @DeleteMapping("/tenant/delete/{tenantId}")
    void deleteTenant(@PathVariable String tenantId);

    @PostMapping("/tenants/{tenantId}/users")
    UserResponse createUser(@PathVariable String tenantId, @RequestBody CreateUserRequest request);

    @GetMapping("/tenants/{tenantId}/users")
    List<UserResponse> getUsersByTenant(@PathVariable String tenantId);

    @PostMapping("/auth/user")
    AuthUserResponse authUser(@RequestBody AuthUserRequest request);
}
