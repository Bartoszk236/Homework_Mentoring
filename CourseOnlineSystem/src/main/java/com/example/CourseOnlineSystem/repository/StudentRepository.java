package com.example.CourseOnlineSystem.repository;

import com.example.CourseOnlineSystem.entity.Course;
import com.example.CourseOnlineSystem.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmail(String email);

    @Query("""
            select s.enrolledCourses from Student s where s.email = :email
            """)
    List<Course> findStudentCoursesByEmail(@Param("email") String email);

    boolean existsByEmail(String email);

    @Query("""
            select s from Student s where size(s.enrolledCourses) > :minCourses
            """)
    List<Student> findActiveStudents(@Param("minCourses") int minCourses);

    @Query("""
            select c from Course c where size(c.students) < :maxStudents
            """)
    List<Course> findCoursesWithLowEnrollment(@Param("maxStudents") int maxStudents);
}
