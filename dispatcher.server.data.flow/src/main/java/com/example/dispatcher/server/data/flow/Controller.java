package com.example.dispatcher.server.data.flow;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class Controller {
    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> test() {
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success"));
    }

    @GetMapping("/blocked/test")
    public ResponseEntity<Map<String, String>> test2() {
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success"));
    }
}
