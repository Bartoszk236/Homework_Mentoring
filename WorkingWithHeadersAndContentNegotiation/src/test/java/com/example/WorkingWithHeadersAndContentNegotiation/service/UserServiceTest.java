package com.example.WorkingWithHeadersAndContentNegotiation.service;

import com.example.WorkingWithHeadersAndContentNegotiation.dto.PublicResponse;
import com.example.WorkingWithHeadersAndContentNegotiation.dto.SimpleResponse;
import com.example.WorkingWithHeadersAndContentNegotiation.entity.Users;
import com.example.WorkingWithHeadersAndContentNegotiation.mapper.Mapper;
import com.example.WorkingWithHeadersAndContentNegotiation.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final Mapper mapper = Mockito.mock(Mapper.class);
    private final UserService userService = new UserService(userRepository, mapper);
    private Users user;

    @BeforeEach
    void setUp() {
        user = new Users();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setUpdatedAt(LocalDateTime.of(2021, 1, 1, 0, 0));
    }

    @Test
    void givenInvalidUserIdWhenGetUserThenThrowException() {
        //given
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        //when/then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.getUserById(1L, "application/json", "1.0", "token", "etag"));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void givenNoAuthorizationRequestWhenGetUserThenReturnPublicData() {
        //given
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        PublicResponse publicResponse = new PublicResponse("John", "Smith");
        when(mapper.toPublicResponse(user)).thenReturn(publicResponse);

        //when
        Object resultNull = userService.getUserById(1L, "application/json", "1.0", null, null);
        Object resultEmpty = userService.getUserById(1L, "application/json", "1.0", " ", null);

        //then
        assertSame(publicResponse, resultNull);
        assertSame(publicResponse, resultEmpty);
        verify(mapper, times(2)).toPublicResponse(user);
    }

    @Test
    void givenUserIdAndPlainTextWhenGetUserThenReturnSimpleResponse() {
        //given
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        //when
        Object result = userService.getUserById(1L, "text/plain", "1.0", "token", null);

        //then
        assertEquals("test@example.com", result);
    }

    @Test
    void givenApiVersion1WhenGetUserByIdThenReturnUser() {
        //given
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        SimpleResponse simpleResponse = new SimpleResponse("John", "Smith", LocalDate.of(2021, 1, 1));
        when(mapper.simpleResponse(user)).thenReturn(simpleResponse);

        //when
        Object result = userService.getUserById(
                1L, "application/json", "1.0", "Bearer token", null
        );

        //then
        assertSame(simpleResponse, result);
    }

    @Test
    void givenApiVersion2WhenGetUserByIdThenReturnUserEntity() {
        //give
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        //when
        Object result = userService.getUserById(
                1L, "application/json", "2.0", "Bearer token", null
        );

        //then
        assertSame(user, result);
    }

    @Test
    void givenUnsupportedApiVersionWhenGetUserByIdThenThrowException() {
        //given
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        //when/then
        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> userService.getUserById(
                        1L, "application/json", "3.0", "Bearer token", null
                )
        );
        assertEquals("Unsupported API version", ex.getMessage());
    }

    @Test
    void givenValidIdWhenGetUserETagThenReturnETag() {
        //given
        String expectedHash = "\"" + user.getUpdatedAt().toString().hashCode() + "\"";
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        //when
        String etag = userService.getUserEtag(1L);

        //then
        assertEquals(expectedHash, etag);
    }
}
