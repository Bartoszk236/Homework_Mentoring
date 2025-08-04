package com.example.ApiVersioningAndBackwardCompatibility.mapper;

import com.example.ApiVersioningAndBackwardCompatibility.dto.UserRequest;
import com.example.ApiVersioningAndBackwardCompatibility.dto.v1.UserResponseV1;
import com.example.ApiVersioningAndBackwardCompatibility.dto.v2.UserResponseV2;
import com.example.ApiVersioningAndBackwardCompatibility.dto.v3.UserResponseV3;
import com.example.ApiVersioningAndBackwardCompatibility.entity.Users;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserMapper {
    public UserResponseV1 toUserResponse(Users users) {
        return UserResponseV1.builder()
                .id(users.getId())
                .name(users.getName())
                .email(users.getEmail())
                .build();
    }

    public Users toUserEntity(UserRequest userRequest, Long id) {
        if (id == null) {
            return Users.builder()
                    .name(userRequest.name())
                    .email(userRequest.email())
                    .build();
        } else {
            return Users.builder()
                    .id(id)
                    .name(userRequest.name())
                    .email(userRequest.email())
                    .build();
        }
    }

    public UserResponseV2 toUserResponseV2(Users users) {
        return UserResponseV2.builder()
                .id(users.getId())
                .name(users.getName())
                .email(users.getEmail())
                .createdAt(users.getCreatedAt())
                .updatedAt(users.getUpdatedAt())
                .lastLogin(users.getLastLogin())
                .activeStaus(users.isActiveStaus())
                .build();
    }

    public Object toUserResponseV3(Users users) {
        Map<String, Object> profile = new HashMap<>();
        Map<String, Object> metadata = new HashMap<>();
        profile.put("id", users.getId());
        profile.put("name", users.getName());
        profile.put("email", users.getEmail());
        metadata.put("createdAt", users.getCreatedAt());
        metadata.put("updatedAt", users.getUpdatedAt());
        metadata.put("lastLogin", users.getLastLogin());
        metadata.put("activeStaus", users.isActiveStaus());
        return new UserResponseV3(profile, metadata);
    }
}
