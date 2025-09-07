package com.example.CourseOnlineSystem.repository;

import com.example.CourseOnlineSystem.entity.Course;
import com.example.CourseOnlineSystem.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class StudentRepositoryTest {
    @Autowired
    private TestEntityManager em;
    @Autowired
    private StudentRepository repository;

    @BeforeEach
    void setUp() {
        Course course1 = new Course()
                .setTitle("Java")
                .setCourseCode("java-1");
        Course course2 = new Course()
                .setTitle("C++")
                .setCourseCode("c++-1");

        Student student1 = new Student()
                .setEmail("bartosz@gmail.com");
        Student student2 = new Student()
                .setEmail("kamil@gmail.com");

        student1.enrollInCourse(course1);
        student1.enrollInCourse(course2);
        student2.enrollInCourse(course1);

        em.persist(student1);
        em.persist(student2);
        em.persist(course1);
        em.persist(course2);
        em.flush();
        em.clear();
    }

    @Test
    void givenStudentWithTwoCoursesWhenFindActiveStudentsThenReturnThisStudent() {
        //when
        List<Student> result = repository.findActiveStudents(1);

        //then
        assertEquals(1, result.size());
        Student studentResult = result.getFirst();

        assertEquals("bartosz@gmail.com", studentResult.getEmail());
    }

    @Test
    void givenCursesWithStudentWhenFindCoursesWithLowEnrollmentThenReturnThisCourse() {
        //when
        List<Course> result = repository.findCoursesWithLowEnrollment(2);

        //then
        assertEquals(1, result.size());
        Course courseResult = result.getFirst();
        assertEquals("c++-1", courseResult.getCourseCode());
    }
}
