package com.example.CourseOnlineSystem.repository;

import com.example.CourseOnlineSystem.entity.Course;
import com.example.CourseOnlineSystem.entity.Student;
import com.example.CourseOnlineSystem.model.EnrollmentStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class EnrollmentRepositoryTest {
    @Autowired
    private EnrollmentRepository repo;
    @Autowired
    private TestEntityManager em;

    @Test
    void getStudentGPAQueryTest() {
        //given
        Course course1 = new Course();
        course1.setCourseCode("JAVA-101");

        Course course2 = new Course();
        course1.setCourseCode("CPP-201");

        Course course3 = new Course();
        course3.setCourseCode("SQL-150");

        em.persist(course1);
        em.persist(course2);
        em.persist(course3);

        Student student = new Student();
        student.setFirstName("Bartosz");
        student.setEmail("bartosz@example.com");
        em.persist(student);

        student.enrollInCourse(course1, EnrollmentStatus.COMPLETED);
        student.enrollInCourse(course2, EnrollmentStatus.COMPLETED);
        student.enrollInCourse(course3, EnrollmentStatus.ACTIVE);

        student.getEnrollments().stream()
                .filter(e -> e.getCourse().equals(course1))
                .findFirst()
                .ifPresent(e -> e.setFinalGrade(new BigDecimal("3.00")));

        student.getEnrollments().stream()
                .filter(e -> e.getCourse().equals(course2))
                .findFirst()
                .ifPresent(e -> e.setFinalGrade(new BigDecimal("5.00")));

        em.flush();
        em.clear();

        Double expectedGPA = 4.00;

        //when
        Double result = repo.getStudentGPA(student.getEmail());

        //then
        assertEquals(expectedGPA, result);
    }

    @Test
    void getTopStudentsQueryTest() {
        //given
        Course javaCourse = new Course();
        javaCourse.setCourseCode("JAVA-101");

        Course cppCourse = new Course();
        cppCourse.setCourseCode("CPP-201");

        em.persist(javaCourse);
        em.persist(cppCourse);

        Student student1 = new Student();
        student1.setFirstName("Bartosz");
        student1.setEmail("bartosz@example.com");

        Student student2 = new Student();
        student2.setFirstName("Kamil");
        student2.setEmail("kamil@example.com");

        em.persist(student1);
        em.persist(student2);

        student1.enrollInCourse(javaCourse, EnrollmentStatus.COMPLETED);
        student1.enrollInCourse(cppCourse, EnrollmentStatus.COMPLETED);

        student1.getEnrollments().stream()
                .filter(e -> e.getCourse().equals(javaCourse))
                .findFirst()
                .ifPresent(e -> e.setFinalGrade(new BigDecimal("4.50")));

        student1.getEnrollments().stream()
                .filter(e -> e.getCourse().equals(cppCourse))
                .findFirst()
                .ifPresent(e -> e.setFinalGrade(new BigDecimal("5.00")));

        student2.enrollInCourse(javaCourse, EnrollmentStatus.COMPLETED);

        student2.getEnrollments().stream()
                .findFirst()
                .ifPresent(e -> e.setFinalGrade(new BigDecimal("3.00")));

        em.flush();
        em.clear();

        //when
        List<Object[]> results = repo.getTopStudents();

        //then
        assertEquals(2, results.size());

        Object[] top = results.get(0);
        Object[] second = results.get(1);

        Student topStudent = (Student) top[0];
        Double topAverage = (Double) top[1];

        assertEquals("Bartosz", topStudent.getFirstName());
        assertEquals(4.75, topAverage);

        Student secondStudent = (Student) second[0];
        Double secondAverage = (Double) second[1];

        assertEquals("Kamil", secondStudent.getFirstName());
        assertEquals(3.00, secondAverage);
    }
}
