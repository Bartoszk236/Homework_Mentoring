package com.example.ApiVersioningAndBackwardCompatibility.dto.v3;

import java.util.Map;

public record UserResponseV3(
        Map<String, Object> profile,
        Map<String, Object> metadata
) {
}
