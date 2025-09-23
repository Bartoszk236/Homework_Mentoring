package com.example.LibraryManagementSystem.dto;

import java.util.List;

public record BookSearchCriteria(
        String title,
        List<String> authorNames,
        String categoryName,
        Integer publishedAfterYear,
        Integer publishedBeforeYear,
        Boolean availableOnly,
        Integer minPages,
        Integer maxPages,
        String isbn,
        Boolean digitalCopyRequired
) {
}
