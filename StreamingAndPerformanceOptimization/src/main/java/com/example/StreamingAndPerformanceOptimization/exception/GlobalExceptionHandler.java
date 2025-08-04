package com.example.StreamingAndPerformanceOptimization.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleException(Exception e) {
        StackTraceElement origin = e.getStackTrace()[0];
        String className = origin.getClassName();
        String methodName = origin.getMethodName();
        String line = String.valueOf(origin.getLineNumber());

        Map<String, String> exceptionData = new HashMap<>();
        exceptionData.put("message", e.getMessage());
        exceptionData.put("exception", e.getClass().getName());
        exceptionData.put("class", className);
        exceptionData.put("method", methodName);
        exceptionData.put("line", line);
        exceptionData.put("timestamp", LocalDateTime.now().toString());
        log.error("Exception in {}.{}():{} → {}", className, methodName, line, e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(exceptionData);
    }
}
