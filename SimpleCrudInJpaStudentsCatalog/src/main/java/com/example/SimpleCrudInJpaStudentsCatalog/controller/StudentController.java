package com.example.SimpleCrudInJpaStudentsCatalog.controller;

import com.example.SimpleCrudInJpaStudentsCatalog.entity.Student;
import com.example.SimpleCrudInJpaStudentsCatalog.model.Status;
import com.example.SimpleCrudInJpaStudentsCatalog.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping("/by-lastname")
    public ResponseEntity<List<Student>> getStudentsByLastname(@RequestParam String lastName) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(studentService.getStudentByLastName(lastName));
    }

    @GetMapping("/by-firstname-and-lastname")
    public ResponseEntity<List<Student>> getStudentsByFirstnameAndLastname(@RequestParam String firstName,
                                                                           @RequestParam String lastName) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(studentService.getStudentByFirstNameAndLastName(firstName, lastName));
    }

    @GetMapping("/count-by-status")
    public ResponseEntity<Long> getCountByStatus(@RequestParam Status status) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(studentService.getCountStudentsByStatus(status));
    }
}
