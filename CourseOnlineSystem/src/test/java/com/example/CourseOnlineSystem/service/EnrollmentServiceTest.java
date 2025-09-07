package com.example.CourseOnlineSystem.service;

import com.example.CourseOnlineSystem.entity.Course;
import com.example.CourseOnlineSystem.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(EnrollmentService.class)
class EnrollmentServiceTest {
    private static final String STUDENT_FIRST_NAME = "John";
    private static final String STUDENT_LAST_NAME = "Doe";
    private static final String STUDENT_EMAIL = "john@exmple.com";
    private static final Integer STUDENT_NUMBER = 1;
    private static final String COURSE_TITLE = "Java";
    private static final String COURSE_DESCRIPTION = "Java Programming";
    private static final String COURSE_CODE = "XYZ-123";
    private static final Integer COURSE_CREDITS = 10;

    @Autowired
    private EnrollmentService service;
    @Autowired
    private TestEntityManager em;

    @Test
    void givenStudentAndCourseInDatabaseWhenEnrollStudentThenStudentIsEnrolled() {
        //given
        Student student = createStudent();
        Course course = createCourse();
        em.persist(student);
        em.persist(course);
        em.flush();
        em.clear();

        //when
        service.enrollStudent(student.getEmail(), course.getCourseCode());
        em.flush();
        em.clear();

        //then
        Student resultStudent = em.find(Student.class, student.getId());
        Course resultCourse = em.find(Course.class, course.getId());

        assertThat(resultStudent.getEnrolledCourses().contains(resultCourse));
        assertThat(resultCourse.getStudents().contains(student));

        checkStudent(resultStudent);
        checkCourse(resultCourse);
    }

    @Test
    void givenNoStudentInDatabaseWhenEnrollStudentThenTrowException() {
        //given
        String studentEmail = "xyz";
        Course course = createCourse();
        em.persist(course);
        em.flush();
        em.clear();

        //when / then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> service.enrollStudent(studentEmail, course.getCourseCode()));

        assertEquals("not found student with email: " + studentEmail, exception.getMessage());
    }

    @Test
    void givenNoCourseInDatabaseWhenEnrollStudentThenTrowException() {
        //given
        Student student = createStudent();
        String courseCode = "XYZ-123";
        em.persist(student);
        em.flush();
        em.clear();

        //when / then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> service.enrollStudent(student.getEmail(), courseCode));

        assertEquals("not found course with code: " + courseCode, exception.getMessage());
    }

    @Test
    void givenCourseAndStudentInDatabaseWhenGetStudentCoursesThenReturnListOfStudentCourses() {
        //given
        Student student = createStudent();
        Course course = createCourse();
        student.enrollInCourse(course);
        em.persist(student);
        em.persist(course);
        em.flush();
        em.clear();

        //when
        List<Course> resultCourses = service.getStudentCourses(student.getEmail());

        //then
        assertEquals(1, resultCourses.size());

        Course resultCourse = resultCourses.getFirst();
        checkCourse(resultCourse);
    }

    @Test
    void givenStudentWithNoCoursesInDatabaseWhenGetStudentCoursesThenThrowException() {
        //given
        Student student = createStudent();
        em.persist(student);
        em.flush();
        em.clear();

        //when /then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> service.getStudentCourses(student.getEmail()));

        assertEquals("student with email: " + student.getEmail() + " don't have any course", exception.getMessage());
    }

    @Test
    void givenStudentWithCourseInDatabaseWhenIsStudentEnrolledThenReturnTrue() {
        //given
        Student student = createStudent();
        Course course = createCourse();
        student.enrollInCourse(course);
        em.persist(student);
        em.persist(course);
        em.flush();
        em.clear();

        //when
        boolean result = service.isStudentEnrolled(student.getEmail(), course.getCourseCode());

        //then
        assertTrue(result);
    }

    @Test
    void givenStudentWithoutCourseInDatabaseWhenIsStudentEnrolledThenReturnFalse() {
        //given
        Student student = createStudent();
        Course course = createCourse();

        em.persist(student);
        em.persist(course);
        em.flush();
        em.clear();

        //when
        boolean result = service.isStudentEnrolled(student.getEmail(), course.getCourseCode());

        //then
        assertFalse(result);
    }

    private void checkStudent(Student student) {
        assertEquals(STUDENT_FIRST_NAME, student.getFirstName());
        assertEquals(STUDENT_LAST_NAME, student.getLastName());
        assertEquals(STUDENT_EMAIL, student.getEmail());
        assertEquals(STUDENT_NUMBER, student.getStudentNumber());
    }

    private void checkCourse(Course course) {
        assertEquals(COURSE_TITLE, course.getTitle());
        assertEquals(COURSE_DESCRIPTION, course.getDescription());
        assertEquals(COURSE_CODE, course.getCourseCode());
        assertEquals(COURSE_CREDITS, course.getCredits());
    }

    private Student createStudent() {
        return new Student()
                .setFirstName(STUDENT_FIRST_NAME)
                .setLastName(STUDENT_LAST_NAME)
                .setEmail(STUDENT_EMAIL)
                .setStudentNumber(STUDENT_NUMBER);
    }

    private Course createCourse() {
        return new Course()
                .setTitle(COURSE_TITLE)
                .setDescription(COURSE_DESCRIPTION)
                .setCourseCode(COURSE_CODE)
                .setCredits(COURSE_CREDITS);
    }
}
