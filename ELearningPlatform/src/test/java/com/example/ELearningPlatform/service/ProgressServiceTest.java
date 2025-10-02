package com.example.ELearningPlatform.service;

import com.example.ELearningPlatform.SampleDataUtils;
import com.example.ELearningPlatform.dto.ProgressDataDto;
import com.example.ELearningPlatform.dto.ProgressStudentDto;
import com.example.ELearningPlatform.entity.*;
import com.example.ELearningPlatform.repository.CourseRepository;
import com.example.ELearningPlatform.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Import({ProgressService.class, SampleDataUtils.class})
class ProgressServiceTest {
    @Autowired
    private ProgressService  progressService;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private SampleDataUtils utils;

    @BeforeEach
    void setUp() {
        utils.initSampleData();
    }

    @Test
    void givenSampleDataWhenGetStudentsProgressByCourseThenReturnListOfProjectionsWithValidData() {
        //given
        Course course = courseRepository.findAll().stream()
                .findFirst()
                .orElseThrow();

        List<ProgressStudentDto> expectedResults = getExpectedProjectionsResultsList(course);

        //when
        List<ProgressStudentDto> result = progressService.getStudentsProgressByCourse(course.getCourseId());

        //then
        assertThat(result).isNotEmpty();
        assertThat(result).containsExactlyInAnyOrderElementsOf(expectedResults);
    }

    @Test
    void givenSampleDataWhenGetProgressDataByStudentAndCourseThenReturnProgressDataDtoForStudentInCourse() {
        //given
        Student student = studentRepository.findAll().stream()
                .findFirst()
                .orElseThrow();

        Enrollment enrollment = student.getEnrollments().stream()
                .findFirst()
                .orElseThrow();

        Course course = enrollment.getCourse();

        Long expectedTotalLessons = (long) course.getLessons().size();
        Integer expectedCompletedLessons = enrollment.getProgress().getCompletedLessons();

        //when
        ProgressDataDto result = progressService.getProgressDataByStudentAndCourse(student.getStudentId(), course.getCourseId());

        //then
        assertNotNull(result);
        assertThat(result.totalLessons()).isEqualTo(expectedTotalLessons);
        assertThat(result.completedLessons()).isEqualTo(expectedCompletedLessons);
    }

    private List<ProgressStudentDto> getExpectedProjectionsResultsList(Course course) {
        Set<Enrollment> enrollments = course.getEnrollments();
        List<ProgressStudentDto> expectedResults = new ArrayList<>();

        enrollments.forEach(enrollment -> {
            String firstName = enrollment.getStudent().getFirstName();
            String lastName = enrollment.getStudent().getLastName();

            Integer completedLessons = enrollment.getProgress().getCompletedLessons();
            Long totalLessons = (long) course.getLessons().size();
            String fullName = firstName + " " + lastName;
            expectedResults.add(new ProgressStudentDto(fullName, completedLessons, totalLessons));
        });
        return expectedResults;
    }
}
