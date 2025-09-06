package com.example.SimpleCrudInJpaStudentsCatalog.repository;

import com.example.SimpleCrudInJpaStudentsCatalog.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static com.example.SimpleCrudInJpaStudentsCatalog.model.Status.*;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class StudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void setUp() {
        studentRepository.deleteAll();

        testEntityManager.persist(Student.builder()
                .firstName("A")
                .lastName("A")
                .status(ACTIVE)
                .enrollmentDate(LocalDateTime.now().minusDays(1))
                .build());

        testEntityManager.persist(Student.builder()
                .firstName("B")
                .lastName("B")
                .status(ACTIVE)
                .enrollmentDate(LocalDateTime.now().minusDays(3))
                .build());

        testEntityManager.persist(Student.builder()
                .firstName("C")
                .lastName("C")
                .status(ACTIVE)
                .enrollmentDate(LocalDateTime.now())
                .build());

        testEntityManager.persist(Student.builder()
                .firstName("D")
                .lastName("D")
                .status(INACTIVE)
                .enrollmentDate(LocalDateTime.now().minusDays(2))
                .build());

        testEntityManager.flush();
    }

    @Test
    void whenFindAllByStatusOrderByEnrollmentDateDescThenFindAllByStatusOrderByEnrollmentDateDesc() {
        // when
        List<Student> result = studentRepository.findByStatusOrderByEnrollmentDateDesc(ACTIVE);

        //then
        assertEquals(3, result.size());
        assertThat(result)
                .extracting(Student::getEnrollmentDate)
                .isSortedAccordingTo(Comparator.reverseOrder());

        assertThat(result.stream().allMatch(s -> s.getStatus().equals(ACTIVE)));
    }

    @Test
    void givenCriteriaWhenFindByStatusThenReturnPageAndSortList() {
        //given
        Pageable pageable = PageRequest.of(0, 2,
                Sort.by("enrollmentDate").ascending());

        //when
        Page<Student> result = studentRepository.findByStatus(ACTIVE, pageable);

        //then
        assertEquals(2, result.getContent().size());
        assertEquals(3, result.getTotalElements());
        assertEquals(2, result.getSize());
        assertEquals(0, result.getNumber());

        assertThat(result)
                .extracting(Student::getEnrollmentDate)
                .isSortedAccordingTo(Comparator.naturalOrder());

        assertThat(result.stream().allMatch(s -> s.getStatus().equals(ACTIVE)));
    }
}
