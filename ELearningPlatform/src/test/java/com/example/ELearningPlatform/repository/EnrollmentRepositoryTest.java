package com.example.ELearningPlatform.repository;

import com.example.ELearningPlatform.entity.Enrollment;
import com.example.ELearningPlatform.model.EnrollmentStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class EnrollmentRepositoryTest {
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private TestEntityManager em;

    @Test
    void test_bulkUpdateEnrollmentsStatus() {
        //given
        Enrollment enrollment = new Enrollment();
        enrollment.setStatus(EnrollmentStatus.IN_PROGRESS);
        em.persist(enrollment);

        Enrollment enrollment2 = new Enrollment();
        enrollment2.setStatus(EnrollmentStatus.IN_PROGRESS);
        em.persist(enrollment2);

        Enrollment enrollment3 = new Enrollment();
        enrollment3.setStatus(EnrollmentStatus.IN_PROGRESS);
        em.persist(enrollment3);

        em.flush();
        em.clear();

        //when
        int result = enrollmentRepository.bulkUpdateEnrollmentsStatus(EnrollmentStatus.COMPLETED);

        //then
        assertEquals(3, result);
        List<Enrollment> enrollments = enrollmentRepository.findAll();
        assertTrue(enrollments.stream()
                .map(Enrollment::getStatus)
                .allMatch(status -> status == EnrollmentStatus.COMPLETED)
        );
    }
}
