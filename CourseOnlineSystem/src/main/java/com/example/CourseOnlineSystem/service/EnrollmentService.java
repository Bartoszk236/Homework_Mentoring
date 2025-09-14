package com.example.CourseOnlineSystem.service;

import com.example.CourseOnlineSystem.entity.Course;
import com.example.CourseOnlineSystem.entity.Enrollment;
import com.example.CourseOnlineSystem.entity.Student;
import com.example.CourseOnlineSystem.repository.CourseRepository;
import com.example.CourseOnlineSystem.repository.EnrollmentRepository;
import com.example.CourseOnlineSystem.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static com.example.CourseOnlineSystem.model.EnrollmentStatus.*;

@Service
@RequiredArgsConstructor
@Transactional
public class EnrollmentService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public void enrollStudentInCourse(String studentEmail, String courseCode) {
        Student student = getPresentStudent(studentEmail);

        Course course = courseRepository.findByCourseCode(courseCode).orElseThrow(
                () -> new IllegalArgumentException("course with code: " + courseCode + " not found")
        );

        if (student.getActiveCourses().contains(course))
            throw new IllegalArgumentException("student is already enrolled in course with code: " + courseCode);

        student.enrollInCourse(course, ACTIVE);
    }

    public void assignGrade(Long enrollmentId, BigDecimal grade) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElseThrow(
                () -> new IllegalArgumentException("enrollment with id: " + enrollmentId + " not found")
        );

        enrollment.setFinalGrade(grade);
        enrollment.setStatus(COMPLETED);
    }

    public void dropStudentFromCourse(String studentEmail, String courseCode) {
        Student student = getPresentStudent(studentEmail);

        Enrollment enrollment = student.getEnrollments()
                .stream()
                .filter(enrollment1 -> enrollment1.getCourse().getCourseCode().equals(courseCode))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "student with email: " + studentEmail + " does not participate in course with code: " + courseCode)
                );

        enrollment.setStatus(DROPPED);
    }

    public List<Enrollment> getStudentTranscript(String studentEmail) {
        Student student = getPresentStudent(studentEmail);

        return student.getEnrollments()
                .stream()
                .filter(enrollment -> enrollment.getStatus().equals(COMPLETED) && enrollment.getFinalGrade() != null)
                .toList();
    }

    private Student getPresentStudent(String studentEmail) {
        return studentRepository.findByEmail(studentEmail).orElseThrow(
                () -> new IllegalArgumentException("student with email: " + studentEmail + " not found")
        );
    }
}
