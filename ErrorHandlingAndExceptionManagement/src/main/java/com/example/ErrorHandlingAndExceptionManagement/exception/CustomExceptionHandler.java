package com.example.ErrorHandlingAndExceptionManagement.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.*;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class CustomExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex, HttpServletRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getLocalizedMessage());
        body.put("time", LocalDateTime.now().toString());
        body.put("url", request.getRequestURL());
        String cid = Optional.ofNullable(request.getHeader("X-Correlation-Id"))
                .orElse(UUID.randomUUID().toString());

        log.error(createLog(request, ex, null));
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .header("X-Correlation-Id", cid)
                .body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<Map<String, String>>> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<Map<String, String>> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> {
                    Map<String, String> m = new HashMap<>();
                    m.put("field", fe.getField());
                    m.put("message", fe.getDefaultMessage());
                    m.put("time", LocalDateTime.now().toString());
                    return m;
                })
                .toList();

        log.error(createLog(request, ex, null));
        return ResponseEntity.status(BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<Map<String, String>>> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest request) {
        List<Map<String, String>> errors = ex.getConstraintViolations().stream()
                .map(cv -> {
                    Map<String, String> m = new HashMap<>();
                    m.put("path", cv.getPropertyPath().toString());
                    m.put("message", cv.getMessage());
                    m.put("time", LocalDateTime.now().toString());
                    return m;
                })
                .toList();
        log.error(createLog(request, ex, null));
        return ResponseEntity.status(BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEntityNotFound(EntityNotFoundException ex, HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        int index = url.lastIndexOf('/');
        String invalidId = url.substring(index + 1);

        String code = "exception.entity_not_found";
        String message = messageSource.getMessage(code, null, request.getLocale());

        log.error(createLog(request, ex, message));
        return ResponseEntity.status(NOT_FOUND)
                .body(Map.of("message", message + " " + invalidId));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolation(DataIntegrityViolationException ex, HttpServletRequest request) {
        String code = "exception.data_integrity_violation";
        String message = ex.getLocalizedMessage();
        if (message == null) message = messageSource.getMessage(code, null, request.getLocale());

        log.error(createLog(request, ex, message));
        return ResponseEntity.status(CONFLICT).body(Map.of("message", message));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(AccessDeniedException ex, HttpServletRequest request) {
        String code = "exception.access_denied";
        String message = messageSource.getMessage(code, null, request.getLocale());

        Map<String, Object> body = new HashMap<>();
        body.put("message", message);
        body.put("time", LocalDateTime.now().toString());
        body.put("url", request.getRequestURL());
        body.put("method", request.getMethod());

        Map<String, String> security = new HashMap<>();
        security.put("userId", request.getUserPrincipal() != null
                ? request.getUserPrincipal().getName()
                : "anonymous");

        body.put("security", security);

        log.error(createLog(request, ex, message));
        return ResponseEntity.status(FORBIDDEN).body(body);
    }

    private String createLog(HttpServletRequest request, Exception exception, String messageFromSource) {
        LinkedHashMap<String, String> log = new LinkedHashMap<>();
        log.put("exception", exception.getClass().getSimpleName());

        String messageFromException = exception.getLocalizedMessage();
        String message = messageFromException != null
                ? messageFromException
                : messageFromSource;
        log.put("message", message);

        log.put("time", LocalDateTime.now().toString());
        log.put("url", request.getRequestURL().toString());
        log.put("method", request.getMethod());
        String user = request.getUserPrincipal() != null
                ? request.getUserPrincipal().getName()
                : "anonymous";
        log.put("user", user);
        return log.toString();
    }
}
