package com.example.CourseOnlineSystem.repository;

import com.example.CourseOnlineSystem.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    @Query("""
            SELECT AVG(e.finalGrade) FROM Enrollment e
            WHERE e.student.email = :email AND e.finalGrade IS NOT NULL
            """)
    Double getStudentGPA(@Param("email") String email);

    @Query("""
            SELECT e.student, AVG(e.finalGrade) FROM Enrollment e
                        WHERE e.finalGrade IS NOT NULL
                                    GROUP BY e.student
                                                ORDER BY AVG(e.finalGrade) DESC
            """)
    List<Object[]> getTopStudents();
}
