package com.example.SimpleCrudInJpaStudentsCatalog.service;

import com.example.SimpleCrudInJpaStudentsCatalog.entity.Student;
import com.example.SimpleCrudInJpaStudentsCatalog.exception.StudentNotFoundException;
import com.example.SimpleCrudInJpaStudentsCatalog.model.Status;
import com.example.SimpleCrudInJpaStudentsCatalog.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    public List<Student> getStudentByLastName(String lastName) {
        List<Student> students = studentRepository.findByLastName(lastName);
        if (students.isEmpty()) {
            throw new StudentNotFoundException("Students with lastName " + lastName + " not found");
        }
        return students;
    }

    public List<Student> getStudentByFirstNameAndLastName(String firstName, String lastName) {
        List<Student> students = studentRepository.findByFirstNameAndLastName(firstName, lastName);
        if (students.isEmpty()) {
            throw new StudentNotFoundException("Students with firstName: " + firstName +
                    " and lastName: " + lastName + " not found");
        }
        return students;
    }

    public Long getCountStudentsByStatus(Status status) {
        return studentRepository.countByStatus(status);
    }
}
