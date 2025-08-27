package com.MultiTenantUserManagementSystem.exception;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ExternalApiException.class)
    public ResponseEntity<Map<String, String>> handleExternalApiException(ExternalApiException e) {
        Map<String, String> map = Map.of(
                "message", e.getMessage()
        );
        return ResponseEntity.status(e.getExternalApiResponseCode())
                .body(map);
    }

    @ExceptionHandler(value = FeignException.class)
    public ResponseEntity<Map<String, String>> handleFeignException() {
        Map<String, String> map = Map.of(
                "message", "error while processing response from external api"
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(map);
    }
}
