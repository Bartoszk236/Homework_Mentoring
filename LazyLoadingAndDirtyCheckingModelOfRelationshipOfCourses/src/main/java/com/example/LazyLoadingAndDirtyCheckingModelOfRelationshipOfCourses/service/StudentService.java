package com.example.LazyLoadingAndDirtyCheckingModelOfRelationshipOfCourses.service;

import com.example.LazyLoadingAndDirtyCheckingModelOfRelationshipOfCourses.entity.Student;
import com.example.LazyLoadingAndDirtyCheckingModelOfRelationshipOfCourses.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    @Transactional
    public void dirtyCheckingTest() {
        Student student = studentRepository.findById(1L).orElseThrow();
        student.setFirstName("new first name");
    }

    public Student getStudent(Long id) {
        return studentRepository.findById(id).orElseThrow();
    }
}
