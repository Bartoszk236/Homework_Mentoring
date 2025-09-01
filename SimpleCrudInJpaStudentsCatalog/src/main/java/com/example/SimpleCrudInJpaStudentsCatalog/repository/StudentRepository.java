package com.example.SimpleCrudInJpaStudentsCatalog.repository;

import com.example.SimpleCrudInJpaStudentsCatalog.entity.Student;
import com.example.SimpleCrudInJpaStudentsCatalog.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByLastName(String lastName);
    List<Student> findByFirstNameAndLastName(String firstName, String lastName);
    Long countByStatus(Status status);
}
