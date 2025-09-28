package com.example.ELearningPlatform.dto;

import com.example.ELearningPlatform.model.CourseLevel;

public record CourseSearchRequest(
        String name,
        String category,
        CourseLevel level,
        Double minAverageRating,
        Long minEnrollmentCount
) {
}
