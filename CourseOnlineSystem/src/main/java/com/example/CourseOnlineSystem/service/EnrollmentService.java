package com.example.CourseOnlineSystem.service;

import com.example.CourseOnlineSystem.entity.Course;
import com.example.CourseOnlineSystem.entity.Student;
import com.example.CourseOnlineSystem.repository.CourseRepository;
import com.example.CourseOnlineSystem.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Transactional
    public void enrollStudent(String studentEmail, String courseCode) {
        Student student = studentRepository.findByEmail(studentEmail).orElseThrow(
                () -> new IllegalArgumentException("not found student with email: " + studentEmail)
        );

        Course course = courseRepository.findByCourseCode(courseCode).orElseThrow(
                () -> new IllegalArgumentException("not found course with code: " + courseCode)
        );

        student.enrollInCourse(course);
    }

    public List<Course> getStudentCourses(String studentEmail) {
        if (!studentRepository.existsByEmail(studentEmail))
            throw new IllegalArgumentException("not found student with email: " + studentEmail);

        List<Course> courses = studentRepository.findStudentCoursesByEmail(studentEmail);

        if (courses.isEmpty())
            throw new IllegalArgumentException("student with email: " + studentEmail + " don't have any course");

        return courses;
    }

    @Transactional
    public boolean isStudentEnrolled(String studentEmail, String courseCode) {
        Student student = studentRepository.findByEmail(studentEmail).orElseThrow(
                () -> new IllegalArgumentException("not found student with email: " + studentEmail)
        );

        Course course = courseRepository.findByCourseCode(courseCode).orElseThrow(
                () -> new IllegalArgumentException("not found course with code: " + courseCode)
        );

        return student.getEnrolledCourses().contains(course);
    }
}
