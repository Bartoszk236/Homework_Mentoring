package com.example.ApiVersioningAndBackwardCompatibility.mapper;

import com.example.ApiVersioningAndBackwardCompatibility.dto.UserRequest;
import com.example.ApiVersioningAndBackwardCompatibility.dto.v1.UserResponseV1;
import com.example.ApiVersioningAndBackwardCompatibility.dto.v2.UserResponseV2;
import com.example.ApiVersioningAndBackwardCompatibility.dto.v3.UserResponseV3;
import com.example.ApiVersioningAndBackwardCompatibility.entity.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserMapperTest {
    private UserMapper mapper;
    private Users sampleUser;
    private LocalDateTime created;
    private LocalDateTime updated;
    private LocalDateTime lastLogin;

    @BeforeEach
    void setUp() {
        mapper = new UserMapper();

        created = LocalDateTime.of(2025, 7, 1, 10, 0);
        updated = LocalDateTime.of(2025, 7, 2, 12, 30);
        lastLogin = LocalDateTime.of(2025, 7, 3, 14, 45);

        sampleUser = Users.builder()
                .id(42L)
                .name("Alice")
                .email("alice@example.com")
                .createdAt(created)
                .updatedAt(updated)
                .lastLogin(lastLogin)
                .activeStaus(true)
                .build();
    }

    @Test
    void givenValidUserWhenToUserResponseThenReturnUserResponseV1() {
        // when
        UserResponseV1 v1 = mapper.toUserResponse(sampleUser);

        // then
        assertNotNull(v1);
        assertEquals(42L, v1.getId());
        assertEquals("Alice", v1.getName());
        assertEquals("alice@example.com", v1.getEmail());
    }

    @Test
    void givenUserRequestWithoutIDWhenToUserEntityThenReturnUserEntity() {
        // given
        UserRequest req = new UserRequest("Bob", "bob@example.com");

        // when
        Users u = mapper.toUserEntity(req, null);

        // then
        assertNotNull(u);
        assertEquals("Bob", u.getName());
        assertEquals("bob@example.com", u.getEmail());
    }

    @Test
    void givenUserRequestWithIDWhenToUserEntityThenReturnUserEntity() {
        // given
        UserRequest req = new UserRequest("Carol", "carol@example.com");

        // when
        Users u = mapper.toUserEntity(req, 7L);

        // then
        assertNotNull(u);
        assertEquals(7L, u.getId());
        assertEquals("Carol", u.getName());
        assertEquals("carol@example.com", u.getEmail());
    }

    @Test
    void givenValidUserWhenToUserResponseV2ThenReturnUserResponseV2() {
        // when
        UserResponseV2 v2 = mapper.toUserResponseV2(sampleUser);

        // then
        assertNotNull(v2);
        assertEquals(42L, v2.getId());
        assertEquals("Alice", v2.getName());
        assertEquals("alice@example.com", v2.getEmail());
        assertEquals(created, v2.getCreatedAt());
        assertEquals(updated, v2.getUpdatedAt());
        assertEquals(lastLogin, v2.getLastLogin());
        assertTrue(v2.isActiveStaus());
    }

    @Test
    void givenValidUserWhenToUserResponseV3ThenReturnUserResponseV3() {
        // when
        UserResponseV3 v3 = (UserResponseV3) mapper.toUserResponseV3(sampleUser);

        // then
        assertNotNull(v3);
        Map<String, Object> profile = v3.profile();
        Map<String, Object> metadata = v3.metadata();

        assertEquals(42L, profile.get("id"));
        assertEquals("Alice", profile.get("name"));
        assertEquals("alice@example.com", profile.get("email"));

        assertEquals(created, metadata.get("createdAt"));
        assertEquals(updated, metadata.get("updatedAt"));
        assertEquals(lastLogin, metadata.get("lastLogin"));
        assertEquals(true, metadata.get("activeStaus"));
    }
}
