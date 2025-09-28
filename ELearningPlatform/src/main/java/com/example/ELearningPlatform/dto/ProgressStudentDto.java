package com.example.ELearningPlatform.dto;

public record ProgressStudentDto(
        String fullName,
        Integer completedLessons,
        Long totalLessons
) {
}
