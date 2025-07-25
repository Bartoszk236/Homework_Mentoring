package com.example.StreamingAndPerformanceOptimization.dto;

import com.example.StreamingAndPerformanceOptimization.entity.Users;

public record UsersResponse(
        Long id,
        String username
) {
    public static UsersResponse from(Users users) {
        return  new UsersResponse(users.getId(), users.getUsername());
    }
}
