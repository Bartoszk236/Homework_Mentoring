package com.example.ApiVersioningAndBackwardCompatibility.contoller;

import com.example.ApiVersioningAndBackwardCompatibility.dto.UserRequest;
import com.example.ApiVersioningAndBackwardCompatibility.model.ApiVersion;
import com.example.ApiVersioningAndBackwardCompatibility.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ControllerV3 {
    private final UserService userService;

    @GetMapping(value = "/api/v3/users")
    public ResponseEntity<Object> getUsersByURL() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsers(ApiVersion.V3));
    }

    @GetMapping(value = "/api/users", headers = "API-Version=3.0")
    public ResponseEntity<Object> getUsersByHeader() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsers(ApiVersion.V3));
    }

    @GetMapping(value = "/api/users", params = "version=3.0")
    public ResponseEntity<Object> getUsersByParams() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsers(ApiVersion.V3));
    }

    @GetMapping(value = "/api/v3/users/{id}")
    public ResponseEntity<Object> getUserByURL(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(ApiVersion.V3, id));
    }

    @GetMapping(value = "/api/users/{id}", headers = "API-Version=3.0")
    public ResponseEntity<Object> getUserByHeader(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(ApiVersion.V3, id));
    }

    @GetMapping(value = "/api/users/{id}", params = "version=3.0")
    public ResponseEntity<Object> getUserByParams(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(ApiVersion.V3, id));
    }

    @DeleteMapping(value = "/api/v3/users/{id}")
    public ResponseEntity<HttpStatus> deleteUserByURL(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping(value = "/api/users/{id}", headers = "API-Version=3.0")
    public ResponseEntity<HttpStatus> deleteUserByHeaders(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping(value = "/api/users/{id}", params = "version=3.0")
    public ResponseEntity<HttpStatus> deleteUserByParams(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(value = "/api/v3/users/{id}")
    public ResponseEntity<Object> updateUserByURL(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        userService.updateUser(ApiVersion.V3, id, userRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(value = "/api/users/{id}", headers = "API-Version=3.0")
    public ResponseEntity<Object> updateUserByHeaders(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        userService.updateUser(ApiVersion.V3, id, userRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(value = "/api/users/{id}", params = "version=3.0")
    public ResponseEntity<Object> updateUserByParams(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        userService.updateUser(ApiVersion.V3, id, userRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(value = "/api/v3/users")
    public ResponseEntity<HttpStatus> createUserByURL(@RequestBody UserRequest userRequest) {
        userService.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(value = "/api/users", headers = "API-Version=3.0")
    public ResponseEntity<HttpStatus> createUserByHeader(@RequestBody UserRequest userRequest) {
        userService.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(value = "/api/users", params = "version=3.0")
    public ResponseEntity<HttpStatus> createUserByParam(@RequestBody UserRequest userRequest) {
        userService.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
