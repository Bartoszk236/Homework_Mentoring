package com.example.LibraryManagementSystem.dto;

import com.example.LibraryManagementSystem.entity.Category;

public record CategoryWithAvgPages(
        Category category,
        Double avgPages
) {
}
