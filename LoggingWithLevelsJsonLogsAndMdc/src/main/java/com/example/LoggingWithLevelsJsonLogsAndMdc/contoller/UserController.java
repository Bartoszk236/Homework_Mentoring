package com.example.LoggingWithLevelsJsonLogsAndMdc.contoller;

import com.example.LoggingWithLevelsJsonLogsAndMdc.entity.User;
import com.example.LoggingWithLevelsJsonLogsAndMdc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        MDC.put("userId", String.valueOf(id));
        MDC.put("operation", "getUser");

        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getUserById(id));
    }
}
