package com.example.OwnDataSourceConfigurationAndConnectionPoolAndMonitoring.controller;

import com.example.OwnDataSourceConfigurationAndConnectionPoolAndMonitoring.entity.User;
import com.example.OwnDataSourceConfigurationAndConnectionPoolAndMonitoring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class ResultsController {
    private final UserRepository userRepository;

    @GetMapping("/slow-query")
    public ResponseEntity<User> slowQuery() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userRepository.findByUsername("Bartosz"));
    }
}
