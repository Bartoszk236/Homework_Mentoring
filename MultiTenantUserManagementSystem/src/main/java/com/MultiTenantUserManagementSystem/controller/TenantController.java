package com.MultiTenantUserManagementSystem.controller;

import com.MultiTenantUserManagementSystem.dto.CreateTenantRequest;
import com.MultiTenantUserManagementSystem.dto.CreateTenantResponse;
import com.MultiTenantUserManagementSystem.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tenant")
public class TenantController {
    private final TenantService tenantService;

    @PostMapping
    public ResponseEntity<CreateTenantResponse> createTenant(@RequestBody CreateTenantRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tenantService.createTenant(request));
    }

    @DeleteMapping("/{tenantId}")
    public ResponseEntity<HttpStatus> deleteTenant(@PathVariable String tenantId) {
        tenantService.deleteTenant(tenantId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
