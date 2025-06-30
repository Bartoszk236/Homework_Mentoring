package com.example.ErrorHandlingAndExceptionManagement.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class CustomExceptionHandlerTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final MessageSource messageSource = mock(MessageSource.class);
    private final CustomExceptionHandler customExceptionHandler = new CustomExceptionHandler(messageSource);
    private final StringBuffer url = new StringBuffer("http://localhost/test");

    @BeforeEach
    void setUp() {
        when(request.getLocale()).thenReturn(Locale.ENGLISH);
        when(request.getRequestURL()).thenReturn(url);
        when(request.getMethod()).thenReturn("POST");
        when(request.getRequestURI()).thenReturn("/test");
    }

    @Test
    void givenExceptionAndExistingCorrelationIdHeaderWhenHandleThenReturnCorrectResponse() {
        // given
        Exception ex = new RuntimeException("boom");
        when(request.getHeader("X-Correlation-Id")).thenReturn("abc-123");

        // when
        ResponseEntity<Map<String, Object>> resp = customExceptionHandler.handleException(ex, request);

        // then
        assertEquals(500, resp.getStatusCode().value());
        assertEquals("abc-123", resp.getHeaders().getFirst("X-Correlation-Id"));

        Map<String, Object> body = resp.getBody();
        assertNotNull(body);
        assertEquals("boom", body.get("message"));
        assertEquals(url, body.get("url"));
        assertNotNull(body.get("time"));
    }

    @Test
    void givenExceptionAndNoCorrelationIdWhenHandleThenReturnResponseWithNewCorrelationId() {
        // given
        Exception ex = new IllegalStateException("oops");
        when(request.getHeader("X-Correlation-Id")).thenReturn(null);

        // when
        ResponseEntity<Map<String, Object>> resp = customExceptionHandler.handleException(ex, request);

        // then
        assertEquals(500, resp.getStatusCode().value());
        String cid = resp.getHeaders().getFirst("X-Correlation-Id");
        assertNotNull(cid);
        assertDoesNotThrow(() -> UUID.fromString(cid));

        Map<String, Object> body = resp.getBody();
        assertNotNull(body);
        assertEquals("oops", body.get("message"));
        assertEquals(url, body.get("url"));
        assertDoesNotThrow(() -> LocalDateTime.parse((CharSequence) body.get("time")));
    }

    @Test
    void givenSingleFieldErrorWhenHandleThenReturnsBadRequestWithErrorDetails() {
        // given
        FieldError fe = new FieldError("product", "productName", "must be between 3 and 20 chars");
        BindingResult br = mock(BindingResult.class);
        when(br.getFieldErrors()).thenReturn(List.of(fe));

        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        when(ex.getBindingResult()).thenReturn(br);

        // when
        ResponseEntity<List<Map<String, String>>> resp = customExceptionHandler.handleValidation(ex, request);

        // then
        assertEquals(400, resp.getStatusCode().value());
        List<Map<String, String>> body = resp.getBody();
        assertNotNull(body);
        assertEquals(1, body.size());

        Map<String, String> error = body.getFirst();
        assertEquals("productName", error.get("field"));
        assertEquals("must be between 3 and 20 chars", error.get("message"));
        assertNotNull(error.get("time"));
        // time is parsable as LocalDateTime
        assertDoesNotThrow(() -> LocalDateTime.parse(error.get("time")));
    }

    @Test
    void givenMultipleFieldErrorsWhenHandleThenReturnsAllErrors() {
        // given
        FieldError fe1 = new FieldError("product", "productName", "must be between 3 and 20 chars");
        FieldError fe2 = new FieldError("product", "productPrice", "must be greater than 0");
        BindingResult br = mock(BindingResult.class);
        when(br.getFieldErrors()).thenReturn(List.of(fe1, fe2));

        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        when(ex.getBindingResult()).thenReturn(br);

        // when
        ResponseEntity<List<Map<String, String>>> resp = customExceptionHandler.handleValidation(ex, request);

        // then
        assertEquals(400, resp.getStatusCode().value());
        List<Map<String, String>> body = resp.getBody();
        assertNotNull(body);
        assertEquals(2, body.size());

        Map<String, String> error1 = body.getFirst();
        assertEquals("productName", error1.get("field"));
        assertEquals("must be between 3 and 20 chars", error1.get("message"));

        Map<String, String> error2 = body.get(1);
        assertEquals("productPrice", error2.get("field"));
        assertEquals("must be greater than 0", error2.get("message"));

        body.forEach(m -> assertDoesNotThrow(
                () -> LocalDateTime.parse(m.get("time"))
        ));
    }

    @Test
    void givenSingleViolationWhenHandleThenReturnsBadRequest() {
        // given
        Path path = mock(Path.class);
        when(path.toString()).thenReturn("user.name");

        ConstraintViolation<?> cv = mock(ConstraintViolation.class);
        when(cv.getPropertyPath()).thenReturn(path);
        when(cv.getMessage()).thenReturn("must not be null");

        ConstraintViolationException ex =
                new ConstraintViolationException(Set.of(cv));

        // when
        ResponseEntity<List<Map<String, String>>> resp =
                customExceptionHandler.handleConstraintViolation(ex, request);

        // then
        assertEquals(400, resp.getStatusCode().value());
        List<Map<String, String>> body = resp.getBody();
        assertNotNull(body);
        assertEquals(1, body.size());

        Map<String, String> error = body.getFirst();
        assertEquals("user.name", error.get("path"));
        assertEquals("must not be null", error.get("message"));
        assertNotNull(error.get("time"));
        assertDoesNotThrow(() -> LocalDateTime.parse(error.get("time")));
    }

    @Test
    void givenMultipleViolationsWhenHandleThenReturnsAllErrors() {
        // given
        Path path1 = mock(Path.class);
        when(path1.toString()).thenReturn("product.price");
        ConstraintViolation<?> cv1 = mock(ConstraintViolation.class);
        when(cv1.getPropertyPath()).thenReturn(path1);
        when(cv1.getMessage()).thenReturn("must be greater than 0");

        Path path2 = mock(Path.class);
        when(path2.toString()).thenReturn("product.quantity");
        ConstraintViolation<?> cv2 = mock(ConstraintViolation.class);
        when(cv2.getPropertyPath()).thenReturn(path2);
        when(cv2.getMessage()).thenReturn("must be positive");

        ConstraintViolationException ex =
                new ConstraintViolationException(Set.of(cv1, cv2));

        // when
        ResponseEntity<List<Map<String, String>>> resp =
                customExceptionHandler.handleConstraintViolation(ex, request);

        // then
        assertEquals(400, resp.getStatusCode().value());
        List<Map<String, String>> body = resp.getBody();
        assertNotNull(body);
        assertEquals(2, body.size());

        Map<String, String> error1 = body.getFirst();
        assertEquals("product.price", error1.get("path"));
        assertEquals("must be greater than 0", error1.get("message"));
        assertDoesNotThrow(() -> LocalDateTime.parse(error1.get("time")));

        Map<String, String> error2 = body.get(1);
        assertEquals("product.quantity", error2.get("path"));
        assertEquals("must be positive", error2.get("message"));
        assertDoesNotThrow(() -> LocalDateTime.parse(error2.get("time")));
    }

    @Test
    void givenExceptionWhenHandleThenReturnsBadRequestAndMessage() {
        // given
        String urlWithId = "http://localhost/42";
        when(request.getRequestURL()).thenReturn(new StringBuffer(urlWithId));
        when(messageSource.getMessage("exception.entity_not_found", null, Locale.ENGLISH))
                .thenReturn("Entity not found with id:");

        EntityNotFoundException ex = new EntityNotFoundException("irrelevant");

        // when
        ResponseEntity<Map<String, String>> resp = customExceptionHandler.handleEntityNotFound(ex, request);

        // then
        assertEquals(404, resp.getStatusCode().value());
        Map<String, String> body = resp.getBody();
        assertNotNull(body);
        assertEquals("Entity not found with id: 42", body.get("message"));
    }

    @Test
    void givenMessageInExceptionWhenHandleThenReturnsBadRequestAndMessageFromException() {
        // given
        String exMessage = "Unique constraint violated";
        DataIntegrityViolationException ex = new DataIntegrityViolationException(exMessage);

        when(messageSource.getMessage(anyString(), any(), any(Locale.class)))
                .thenReturn("from messages.properties");

        // when
        ResponseEntity<Map<String, String>> resp =
                customExceptionHandler.handleDataIntegrityViolation(ex, request);

        // then
        assertEquals(409, resp.getStatusCode().value());
        Map<String, String> body = resp.getBody();
        assertNotNull(body);
        assertEquals(exMessage, body.get("message"));

        verify(messageSource, never())
                .getMessage(anyString(), any(), any(Locale.class));
    }

    @Test
    void givenExceptionWithNullMessageWhenHandleThenReturnsBadRequestAndMessageFromMessageSource() {
        // given
        DataIntegrityViolationException ex = new DataIntegrityViolationException(null);
        String resolved = "Produkt już istnieje";
        when(messageSource.getMessage(
                eq("exception.data_integrity_violation"),
                isNull(),
                eq(Locale.ENGLISH)))
                .thenReturn(resolved);

        // when
        ResponseEntity<Map<String, String>> resp =
                customExceptionHandler.handleDataIntegrityViolation(ex, request);

        // then
        assertEquals(409, resp.getStatusCode().value());
        Map<String, String> body = resp.getBody();
        assertNotNull(body);
        assertEquals(resolved, body.get("message"));

        verify(messageSource, times(1))
                .getMessage("exception.data_integrity_violation", null, Locale.ENGLISH);
    }

    @Test
    void givenAuthenticatedUserWhenHandleThenReturnMessageWithUserName() {
        // given
        when(messageSource.getMessage("exception.access_denied", null, Locale.ENGLISH))
                .thenReturn("Access denied");
        Principal principal = () -> "john.doe";
        when(request.getUserPrincipal()).thenReturn(principal);
        AccessDeniedException ex = new AccessDeniedException(null);

        // when
        ResponseEntity<Map<String, Object>> resp =
                customExceptionHandler.handleAccessDenied(ex, request);

        // then
        assertEquals(403, resp.getStatusCode().value());

        Map<String, Object> body = resp.getBody();
        assertNotNull(body);
        assertEquals("Access denied", body.get("message"));
        assertEquals("http://localhost/forbidden", body.get("url").toString());
        assertEquals("POST", body.get("method"));
        assertNotNull(body.get("time"));
        assertDoesNotThrow(() -> LocalDateTime.parse((CharSequence) body.get("time")));

        @SuppressWarnings("unchecked")
        Map<String, String> sec = (Map<String, String>) body.get("security");
        assertEquals("john.doe", sec.get("userId"));

        verify(messageSource).getMessage("exception.access_denied", null, Locale.ENGLISH);
    }

    @Test
    void givenUnAuthenticatedUserWhenHandleThenReturnMessageWithAnonymous() {
        // given
        when(messageSource.getMessage("exception.access_denied", null, Locale.ENGLISH))
                .thenReturn("zabroniony dostep");
        when(request.getUserPrincipal()).thenReturn(null);
        AccessDeniedException ex = new AccessDeniedException("denied");

        // when
        ResponseEntity<Map<String, Object>> resp =
                customExceptionHandler.handleAccessDenied(ex, request);

        // then
        assertEquals(403, resp.getStatusCode().value());

        Map<String, Object> body = resp.getBody();
        assertNotNull(body);
        assertEquals("Zabroniony dostęp", body.get("message"));

        @SuppressWarnings("unchecked")
        Map<String, String> sec = (Map<String, String>) body.get("security");
        assertEquals("anonymous", sec.get("userId"));

        // method and url remain correct
        assertEquals("POST", body.get("method"));
        assertEquals("http://localhost/forbidden", body.get("url").toString());
    }
}
