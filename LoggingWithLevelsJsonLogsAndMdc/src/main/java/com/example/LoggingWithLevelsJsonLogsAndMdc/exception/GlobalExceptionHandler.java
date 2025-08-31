package com.example.LoggingWithLevelsJsonLogsAndMdc.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFoundException(
            UserNotFoundException ex, HttpServletRequest request) {
        HttpStatus responseStatus = HttpStatus.NOT_FOUND;

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", System.currentTimeMillis());
        response.put("message", ex.getMessage());
        response.put("code", responseStatus.value());
        response.put("invalidId", MDC.get("userId"));
        response.put("operation", MDC.get("operation"));
        response.put("exception", ex.getClass().getSimpleName());
        response.put("path", request.getRequestURI());

        log.error("UserNotFoundException", ex);

        return ResponseEntity.status(responseStatus)
                .body(response);
    }
}
