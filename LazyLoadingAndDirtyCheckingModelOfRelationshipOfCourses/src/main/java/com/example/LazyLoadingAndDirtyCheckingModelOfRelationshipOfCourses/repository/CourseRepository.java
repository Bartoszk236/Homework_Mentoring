package com.example.LazyLoadingAndDirtyCheckingModelOfRelationshipOfCourses.repository;

import com.example.LazyLoadingAndDirtyCheckingModelOfRelationshipOfCourses.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("select c from Course c join c.students s where s.id = :studentId")
    Optional<Course> findByStudent(@Param("studentId") Long studentId);
}
