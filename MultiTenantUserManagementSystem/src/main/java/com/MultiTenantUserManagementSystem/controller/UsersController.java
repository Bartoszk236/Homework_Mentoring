package com.MultiTenantUserManagementSystem.controller;

import com.MultiTenantUserManagementSystem.dto.*;
import com.MultiTenantUserManagementSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UsersController {
    private final UserService userService;

    @PostMapping("create/{tenantId}")
    public ResponseEntity<UserResponse> createUser(@PathVariable String tenantId,
                                                   @RequestBody CreateUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.createUser(tenantId, request));
    }

    @GetMapping("/{tenantId}")
    public ResponseEntity<List<UserResponse>> getAllUsers(@PathVariable String tenantId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getUsersByTenantId(tenantId));
    }

    @PostMapping("/auth")
    public ResponseEntity<AuthUserResponse> authUser(@RequestBody AuthUserRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.authUser(request));
    }

    @PostMapping("/create/{tenantId}/bulk")
    public ResponseEntity<List<UserCreateBulkResponse>> createUserBulk(@PathVariable String tenantId, @RequestBody List<CreateUserRequest> request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.createUserBulk(tenantId, request));
    }
}
