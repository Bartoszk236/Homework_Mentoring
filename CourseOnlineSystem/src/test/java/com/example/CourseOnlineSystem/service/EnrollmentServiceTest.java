package com.example.CourseOnlineSystem.service;

import com.example.CourseOnlineSystem.entity.Course;
import com.example.CourseOnlineSystem.entity.Enrollment;
import com.example.CourseOnlineSystem.entity.Student;
import com.example.CourseOnlineSystem.model.EnrollmentStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.List;

import static com.example.CourseOnlineSystem.model.EnrollmentStatus.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(EnrollmentService.class)
public class EnrollmentServiceTest {
    @Autowired
    private EnrollmentService service;
    @Autowired
    private TestEntityManager em;

    @Test
    void givenStudentAlreadyEnrolledWhenEnrollStudentInCourseThenThrowException() {
        //given
        String studentEmail = "student1";
        String courseCode = "courseCode";

        Student student = new Student()
                .setEmail(studentEmail);

        Course course = new Course()
                .setCourseCode(courseCode);

        student.enrollInCourse(course, ACTIVE);
        em.persist(course);
        em.persist(student);
        em.flush();
        em.clear();

        //when / then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> service.enrollStudentInCourse(studentEmail, courseCode)
        );

        assertEquals("student is already enrolled in course with code: " + courseCode, exception.getMessage());
    }

    @Test
    void givenValidStudentAndCourseWhenEnrollStudentInCourseThenEnrolledInCourse() {
        //given
        String studentEmail = "student1";
        String courseCode = "courseCode";

        Student student = new Student()
                .setEmail(studentEmail);

        Course course = new Course()
                .setCourseCode(courseCode);

        em.persist(course);
        em.persist(student);
        em.flush();
        em.clear();

        //when
        service.enrollStudentInCourse(studentEmail, courseCode);
        em.flush();
        em.clear();

        //then
        Student result = em.find(Student.class, student.getId());
        assertEquals(studentEmail, result.getEmail());

        Enrollment enrollment = result.getEnrollments().stream().findFirst().get();
        assertEquals(ACTIVE, enrollment.getStatus());

        assertEquals(courseCode, enrollment.getCourse().getCourseCode());
    }

    @Test
    void givenNoEnrollmentInDatabaseWhenAssignGradeThenThrowException() {
        //given
        Long enrollmentId = 1L;
        BigDecimal grade = new BigDecimal("5.0");

        //when / then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> service.assignGrade(enrollmentId, grade)
        );

        assertEquals("enrollment with id: " + enrollmentId + " not found", exception.getMessage());
    }

    @Test
    void givenValidDataInDatabaseWhenAssignGradeThenSaveEnrollmentWithNewData() {
        //given
        Enrollment enrollment = new Enrollment()
                .setStatus(ACTIVE);
        em.persist(enrollment);
        em.flush();
        em.clear();

        BigDecimal grade = new BigDecimal("5.00");

        //when
        service.assignGrade(enrollment.getId(), grade);
        em.flush();
        em.clear();

        //then
        Enrollment result = em.find(Enrollment.class, enrollment.getId());
        assertEquals(COMPLETED, result.getStatus());
        assertEquals(grade, result.getFinalGrade());
    }

    @Test
    void givenStudentWithCourseWhenDropStudentFromCourseThenChangeStatusToDropped() {
        //given
        String studentEmail = "student1";
        String courseCode = "courseCode";
        String courseCode2 = "courseCode2";

        Student student = new Student()
                .setEmail(studentEmail);

        Course course = new Course()
                .setCourseCode(courseCode);

        Course course2 = new Course()
                .setCourseCode(courseCode2);

        student.enrollInCourse(course, ACTIVE);
        student.enrollInCourse(course2, ACTIVE);
        em.persist(course);
        em.persist(course2);
        em.persist(student);
        em.flush();
        em.clear();

        //when
        service.dropStudentFromCourse(studentEmail, courseCode);
        em.flush();
        em.clear();

        //then
        Student result = em.find(Student.class, student.getId());
        assertEquals(studentEmail, result.getEmail());

        Enrollment enrollmentWithCourse = result.getEnrollments()
                .stream()
                .filter(e -> e.getCourse().getCourseCode().equals(courseCode))
                .findFirst().get();

        assertEquals(DROPPED, enrollmentWithCourse.getStatus());
        assertEquals(courseCode, enrollmentWithCourse.getCourse().getCourseCode());

        Enrollment enrollmentWithCourse2 = result.getEnrollments()
                .stream()
                .filter(e -> e.getCourse().getCourseCode().equals(courseCode2))
                .findFirst().get();

        assertEquals(ACTIVE, enrollmentWithCourse2.getStatus());
        assertEquals(courseCode2, enrollmentWithCourse2.getCourse().getCourseCode());
    }

    @Test
    void givenStudentWithCompletedCoursesWhenGetStudentTranscriptThenReturnEndedCourses() {
        //given
        Course course1 = new Course()
                .setCourseCode("JAVA-101")
                .setTitle("Java Basics")
                .setDescription("Intro to Java")
                .setCredits(5);
        Course course2 = new Course()
                .setCourseCode("CPP-201")
                .setTitle("C++ Fundamentals")
                .setDescription("Core C++")
                .setCredits(6);
        Course course3 = new Course()
                .setCourseCode("SQL-150")
                .setTitle("SQL Essentials")
                .setDescription("Relational DB basics")
                .setCredits(3);

        em.persist(course1);
        em.persist(course2);
        em.persist(course3);

        Student student = new Student()
                .setFirstName("Bartosz")
                .setEmail("bartosz@example.com")
                .setStudentNumber(12345);
        em.persist(student);

        student.enrollInCourse(course1, EnrollmentStatus.COMPLETED);
        student.enrollInCourse(course2, EnrollmentStatus.COMPLETED);
        student.enrollInCourse(course3, EnrollmentStatus.ACTIVE);

        student.getEnrollments().stream()
                .filter(e -> e.getCourse().equals(course1))
                .findFirst()
                .ifPresent(e -> e.setFinalGrade(new BigDecimal("4.50")));

        student.getEnrollments().stream()
                .filter(e -> e.getCourse().equals(course2))
                .findFirst()
                .ifPresent(e -> e.setFinalGrade(new BigDecimal("5.00")));

        em.flush();
        em.clear();

        //when
        List<Enrollment> result = service.getStudentTranscript(student.getEmail());

        //then
        assertEquals(2, result.size());
        assertTrue(result
                .stream()
                .allMatch(e -> e.getStatus().equals(COMPLETED))
        );
    }
}
