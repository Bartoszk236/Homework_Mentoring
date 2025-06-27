package com.example.WorkingWithHeadersAndContentNegotiation.dto;

import java.time.LocalDate;

public record SimpleResponse(
        String firstName,
        String lastName,
        LocalDate birthDate
) {
}
