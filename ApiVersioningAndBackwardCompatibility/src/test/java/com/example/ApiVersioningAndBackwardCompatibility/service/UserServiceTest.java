package com.example.ApiVersioningAndBackwardCompatibility.service;

import com.example.ApiVersioningAndBackwardCompatibility.dto.UserRequest;
import com.example.ApiVersioningAndBackwardCompatibility.dto.v1.UserResponseV1;
import com.example.ApiVersioningAndBackwardCompatibility.dto.v2.UserResponseV2;
import com.example.ApiVersioningAndBackwardCompatibility.dto.v3.UserResponseV3;
import com.example.ApiVersioningAndBackwardCompatibility.entity.Users;
import com.example.ApiVersioningAndBackwardCompatibility.mapper.UserMapper;
import com.example.ApiVersioningAndBackwardCompatibility.model.ApiVersion;
import com.example.ApiVersioningAndBackwardCompatibility.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final UserMapper mapper = Mockito.mock(UserMapper.class);
    private final UserService userService = new UserService(userRepository, mapper);

    private Users entity;
    private UserResponseV1 dtoV1;
    private UserResponseV2 dtoV2;
    private UserResponseV3 dtoV3;
    private UserRequest request;

    @BeforeEach
    void init() {
        entity = Users.builder()
                .id(1L)
                .name("Alice")
                .email("alice@example.com")
                .createdAt(LocalDateTime.of(2025, 7, 1, 10, 0))
                .updatedAt(LocalDateTime.of(2025, 7, 2, 11, 0))
                .lastLogin(LocalDateTime.of(2025, 7, 3, 12, 0))
                .activeStaus(true)
                .build();

        dtoV1 = UserResponseV1.builder()
                .id(1L).name("Alice").email("alice@example.com").build();

        dtoV2 = UserResponseV2.builder()
                .id(1L).name("Alice").email("alice@example.com")
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .lastLogin(entity.getLastLogin())
                .activeStaus(entity.isActiveStaus())
                .build();

        dtoV3 = new UserResponseV3(
                Map.of(
                        "id", 1L,
                        "name", "Alice",
                        "email", "alice@example.com"
                ),
                Map.of(
                        "createdAt", entity.getCreatedAt(),
                        "updatedAt", entity.getUpdatedAt(),
                        "lastLogin", entity.getLastLogin(),
                        "activeStaus", entity.isActiveStaus()
                )
        );

        request = new UserRequest("Bob", "bob@example.com");
    }

    @Test
    void getUsersV1ReturnsMappedList() {
        // given
        when(userRepository.findAll()).thenReturn(List.of(entity));
        when(mapper.toUserResponse(entity)).thenReturn(dtoV1);

        // when
        var result = userService.getUsers(ApiVersion.V1);
        @SuppressWarnings("unchecked")
        List<UserResponseV1> list = (List<UserResponseV1>) result;

        // then
        assertEquals(1, list.size());
        assertSame(dtoV1, list.getFirst());
        verify(userRepository).findAll();
        verify(mapper).toUserResponse(entity);
    }

    @Test
    void getUsersV2ReturnsMappedList() {
        // given
        when(userRepository.findAll()).thenReturn(List.of(entity));
        when(mapper.toUserResponseV2(entity)).thenReturn(dtoV2);

        // when
        @SuppressWarnings("unchecked")
        var list = (List<UserResponseV2>) userService.getUsers(ApiVersion.V2);

        // then
        assertEquals(1, list.size());
        assertSame(dtoV2, list.getFirst());
        verify(mapper).toUserResponseV2(entity);
    }

    @Test
    void getUsersV3ReturnsMappedList() {
        // given
        when(userRepository.findAll()).thenReturn(List.of(entity));
        when(mapper.toUserResponseV3(entity)).thenReturn(dtoV3);

        // when
        @SuppressWarnings("unchecked")
        var list = (List<UserResponseV3>) userService.getUsers(ApiVersion.V3);

        // then
        assertEquals(1, list.size());
        assertSame(dtoV3, list.getFirst());
        verify(mapper).toUserResponseV3(entity);
    }

    @Test
    void getUserV1ExistingReturnsV1() {
        // given
        when(userRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toUserResponse(entity)).thenReturn(dtoV1);

        // when
        var result = userService.getUser(ApiVersion.V1, 1L);

        // then
        assertSame(dtoV1, result);
        verify(mapper).toUserResponse(entity);
    }

    @Test
    void getUserV2ExistingReturnsV2() {
        // given
        when(userRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toUserResponseV2(entity)).thenReturn(dtoV2);

        // when
        var result = userService.getUser(ApiVersion.V2, 1L);

        // then
        assertSame(dtoV2, result);
        verify(mapper).toUserResponseV2(entity);
    }

    @Test
    void getUserV3ExistingReturnsV3() {
        // given
        when(userRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toUserResponseV3(entity)).thenReturn(dtoV3);

        // when
        var result = userService.getUser(ApiVersion.V3, 1L);

        // then
        assertSame(dtoV3, result);
        verify(mapper).toUserResponseV3(entity);
    }

    @Test
    void getUserNotFoundThrowsEntityNotFound() {
        // given
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        // when/then
        assertThrows(EntityNotFoundException.class,
                () -> userService.getUser(ApiVersion.V1, 99L));
    }

    @Test
    void deleteUserDelegatesToRepository() {
        // when
        userService.deleteUser(5L);

        // then
        verify(userRepository).deleteById(5L);
    }

    @Test
    void updateUserExistingSavesUpdatedEntity() {
        // given
        when(userRepository.findById(2L)).thenReturn(Optional.of(entity));
        Users updated = Users.builder().id(2L).name("Bob").email("bob@example.com").build();
        when(mapper.toUserEntity(request, 2L)).thenReturn(updated);

        // when
        userService.updateUser(ApiVersion.V1, 2L, request);

        // then
        verify(mapper).toUserEntity(request, 2L);
        verify(userRepository).save(updated);
    }

    @Test
    void updateUserNotFoundThrowsEntityNotFound() {
        // given
        when(userRepository.findById(3L)).thenReturn(Optional.empty());

        // when/then
        assertThrows(EntityNotFoundException.class,
                () -> userService.updateUser(ApiVersion.V2, 3L, request));
        verify(mapper, never()).toUserEntity(any(), any());
        verify(userRepository, never()).save(any());
    }

    @Test
    void createUserSavesNewEntity() {
        // given
        Users toSave = Users.builder().name("Bob").email("bob@example.com").build();
        when(mapper.toUserEntity(request, null)).thenReturn(toSave);

        // when
        userService.createUser(request);
        // then
        verify(mapper).toUserEntity(request, null);
        verify(userRepository).save(toSave);
    }
}
