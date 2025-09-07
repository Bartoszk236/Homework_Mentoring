package com.example.CourseOnlineSystem.entity;

import com.example.CourseOnlineSystem.model.EnrollmentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "enrollments")
@Getter
@NoArgsConstructor
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;
    @Column(name = "enrollment_date")
    private LocalDate enrollmentDate;
    @Column(name = "final_grade", scale = 2)
    private BigDecimal finalGrade;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EnrollmentStatus status;

    Enrollment(Student student, Course course, LocalDate enrollmentDate, EnrollmentStatus status) {
        this.student = student;
        this.course = course;
        this.enrollmentDate = enrollmentDate;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Enrollment that = (Enrollment) o;
        return Objects.equals(student, that.student) && Objects.equals(course, that.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, course);
    }

    public Enrollment setFinalGrade(BigDecimal finalGrade) {
        this.finalGrade = finalGrade;
        return this;
    }

    public Enrollment setStatus(EnrollmentStatus status) {
        this.status = status;
        return this;
    }
}
