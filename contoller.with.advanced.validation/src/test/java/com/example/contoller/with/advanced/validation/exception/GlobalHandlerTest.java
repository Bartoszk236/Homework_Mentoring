package com.example.contoller.with.advanced.validation.exception;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class GlobalHandlerTest {
    private final GlobalHandler globalHandler = new GlobalHandler();

    @Test
    void givenExceptionWithMessageWhenHandleExceptionThenReturn500CodeAndMessage() {
        //given
        Exception exception = new RuntimeException("error");

        //when
        ResponseEntity<Map<String, String>> response = globalHandler.handleException(exception);

        //then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().containsKey("message"));
        assertEquals("error", response.getBody().get("message"));
    }
}
