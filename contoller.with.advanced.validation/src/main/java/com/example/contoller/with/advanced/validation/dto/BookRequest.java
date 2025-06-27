package com.example.contoller.with.advanced.validation.dto;

import com.example.contoller.with.advanced.validation.annotation.AuthorAnnotation;
import com.example.contoller.with.advanced.validation.annotation.CustomISBN;
import com.example.contoller.with.advanced.validation.validation.ValidationGroups;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record BookRequest(
        @NotNull(message = "title is required", groups = ValidationGroups.class)
        String title,
        @NotNull(message = "author is required", groups = ValidationGroups.class)
        @AuthorAnnotation(groups = ValidationGroups.class)
        String author,
        @CustomISBN(groups = ValidationGroups.class)
        String isbn,
        @DecimalMin(value = "0", inclusive = false, message = "price must be greater than 0", groups = ValidationGroups.class)
        @DecimalMax(value = "10000", inclusive = false, message = "price must be less then 10 000", groups = ValidationGroups.class)
        BigDecimal price,
        @Past(message = "date must be a past", groups = ValidationGroups.class)
        LocalDate publishDate,
        @NotNull(message = "description cannot be null", groups = ValidationGroups.class)
        String description
) {
}
