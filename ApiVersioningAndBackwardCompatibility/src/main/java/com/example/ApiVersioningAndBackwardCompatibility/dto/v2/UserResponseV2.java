package com.example.ApiVersioningAndBackwardCompatibility.dto.v2;

import com.example.ApiVersioningAndBackwardCompatibility.dto.v1.UserResponseV1;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@Getter
public class UserResponseV2 extends UserResponseV1 {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLogin;
    private boolean activeStaus;
}
