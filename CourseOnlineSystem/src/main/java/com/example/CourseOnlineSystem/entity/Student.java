package com.example.CourseOnlineSystem.entity;

import com.example.CourseOnlineSystem.model.EnrollmentStatus;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "students")
@Getter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "student_number")
    private Integer studentNumber;
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Enrollment> enrollments = new HashSet<>();

    public void enrollInCourse(Course course, EnrollmentStatus enrollmentStatus) {
        Enrollment enrollment = new Enrollment(this, course, LocalDate.now(), enrollmentStatus);
        this.enrollments.add(enrollment);
        course.getEnrollments().add(enrollment);
    }

    public List<Course> getActiveCourses() {
        return this.enrollments.stream()
                .filter(enrollment -> enrollment.getStatus().equals(EnrollmentStatus.ACTIVE))
                .map(Enrollment::getCourse)
                .toList();
    }

    public Student setEmail(String email) {
        this.email = email;
        return this;
    }

    public Student setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public Student setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Student setStudentNumber(Integer studentNumber) {
        this.studentNumber = studentNumber;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(email, student.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }
}
