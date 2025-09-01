package com.example.LazyLoadingAndDirtyCheckingModelOfRelationshipOfCourses.controller;

import com.example.LazyLoadingAndDirtyCheckingModelOfRelationshipOfCourses.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping("/test")
    public ResponseEntity<HttpStatus> test() {
        courseService.lazyLoadTest();
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }
}
