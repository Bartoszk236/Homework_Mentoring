package com.example.LazyLoadingAndDirtyCheckingModelOfRelationshipOfCourses.repository;

import com.example.LazyLoadingAndDirtyCheckingModelOfRelationshipOfCourses.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
