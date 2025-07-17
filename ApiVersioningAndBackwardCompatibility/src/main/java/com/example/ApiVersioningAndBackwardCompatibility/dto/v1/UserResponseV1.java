package com.example.ApiVersioningAndBackwardCompatibility.dto.v1;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class UserResponseV1 {
    private Long id;
    private String name;
    private String email;
}
