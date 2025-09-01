package com.example.LazyLoadingAndDirtyCheckingModelOfRelationshipOfCourses.controller;

import com.example.LazyLoadingAndDirtyCheckingModelOfRelationshipOfCourses.entity.Student;
import com.example.LazyLoadingAndDirtyCheckingModelOfRelationshipOfCourses.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping("/test")
    public ResponseEntity<HttpStatus> test() {
        studentService.dirtyCheckingTest();
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(studentService.getStudent(id));
    }
}
