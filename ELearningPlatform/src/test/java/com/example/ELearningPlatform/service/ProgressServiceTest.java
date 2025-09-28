package com.example.ELearningPlatform.service;

import com.example.ELearningPlatform.dto.ProgressDataDto;
import com.example.ELearningPlatform.dto.ProgressStudentDto;
import com.example.ELearningPlatform.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Import(ProgressService.class)
class ProgressServiceTest {
    @Autowired
    private ProgressService  progressService;
    @Autowired
    private TestEntityManager em;

    @Test
    void test_getStudentsProgressByCourse() {
        //given
        Course course = new Course();
        em.persist(course);

        Lesson lesson = new Lesson();
        lesson.setCourse(course);
        em.persist(lesson);

        Lesson lesson2 = new Lesson();
        lesson2.setCourse(course);
        em.persist(lesson2);

        Student student = new Student();
        student.setFirstName("John");
        student.setLastName("Doe");
        em.persist(student);

        Student student2 = new Student();
        student2.setFirstName("Richard");
        student2.setLastName("Money");
        em.persist(student2);

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        em.persist(enrollment);

        Enrollment enrollment2 = new Enrollment();
        enrollment2.setStudent(student2);
        enrollment2.setCourse(course);
        em.persist(enrollment2);

        Progress progress = new Progress();
        progress.setCompletedLessons(2);
        progress.setEnrollment(enrollment);
        em.persist(progress);

        Progress progress2 = new Progress();
        progress2.setCompletedLessons(1);
        progress2.setEnrollment(enrollment2);
        em.persist(progress2);

        em.flush();
        em.clear();

        //when
        List<ProgressStudentDto> result = progressService.getStudentsProgressByCourse(course.getCourseId());

        //then
        assertThat(result).hasSize(2);

        ProgressStudentDto first = result.stream()
                .filter(dto -> dto.fullName().equals("John Doe")).findFirst().get();
        assertNotNull(first);
        assertThat(first.completedLessons()).isEqualTo(2);
        assertThat(first.totalLessons()).isEqualTo(2);

        ProgressStudentDto second = result.stream()
                .filter(dto -> dto.fullName().equals("Richard Money")).findFirst().get();
        assertNotNull(second);
        assertThat(second.completedLessons()).isEqualTo(1);
        assertThat(second.totalLessons()).isEqualTo(2);
    }

    @Test
    void test_getProgressDataByStudentAndCourse() {
        //given
        Course course = new Course();
        em.persist(course);

        Lesson lesson = new Lesson();
        lesson.setCourse(course);
        em.persist(lesson);

        Lesson lesson2 = new Lesson();
        lesson2.setCourse(course);
        em.persist(lesson2);

        Lesson lesson3 = new Lesson();
        lesson3.setCourse(course);
        em.persist(lesson3);

        Student student = new Student();
        student.setFirstName("John");
        student.setLastName("Doe");
        em.persist(student);

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        em.persist(enrollment);

        Progress progress = new Progress();
        progress.setCompletedLessons(2);
        progress.setEnrollment(enrollment);
        em.persist(progress);

        em.flush();
        em.clear();

        Long expectedTotalLessons = 3L;
        Integer expectedCompletedLessons = 2;

        //when
        ProgressDataDto result = progressService.getProgressDataByStudentAndCourse(student.getStudentId(), course.getCourseId());

        //then
        assertNotNull(result);
        assertThat(result.totalLessons()).isEqualTo(expectedTotalLessons);
        assertThat(result.completedLessons()).isEqualTo(expectedCompletedLessons);
    }
}
