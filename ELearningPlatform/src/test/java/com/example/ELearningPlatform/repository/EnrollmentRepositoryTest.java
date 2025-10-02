package com.example.ELearningPlatform.repository;

import com.example.ELearningPlatform.SampleDataUtils;
import com.example.ELearningPlatform.entity.Enrollment;
import com.example.ELearningPlatform.model.EnrollmentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import(SampleDataUtils.class)
class EnrollmentRepositoryTest {
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private SampleDataUtils utils;

    @BeforeEach
    void setUp() {
        utils.initSampleData();
    }

    @Test
    void givenEnrollmentsWithStatusInProgressWhenBulkUpdateEnrollmentsStatusThenAllRecordsInDatabaseHaveNewStatus() {
        //when
        int result = enrollmentRepository.bulkUpdateEnrollmentsStatus(EnrollmentStatus.COMPLETED);

        //then
        assertThat(result).isGreaterThan(0);
        assertTrue(enrollmentRepository.findAll()
                .stream()
                .map(Enrollment::getStatus)
                .allMatch(status -> status == EnrollmentStatus.COMPLETED)
        );
    }
}
