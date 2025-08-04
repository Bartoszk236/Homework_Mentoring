package com.example.RateLimitingAndMonitoring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ApiController {

    // na potrzeby zadania wymagany jest header "X-Role" gdzie symulujemy rangę użytkownika | unauthorized, authorized, premium
    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> test() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("result", "success"));
    }
}
