package com.example.ELearningPlatform.repository;

import com.example.ELearningPlatform.entity.Enrollment;
import com.example.ELearningPlatform.model.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("""
            update Enrollment e
            set e.status = :newStatus
            """)
    int bulkUpdateEnrollmentsStatus(@Param("newStatus") EnrollmentStatus newStatus);

    boolean existsByStudent_StudentIdAndCourse_CourseId(Long studentId, Long courseId);
}
