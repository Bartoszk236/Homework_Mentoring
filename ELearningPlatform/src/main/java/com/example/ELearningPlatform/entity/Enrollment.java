package com.example.ELearningPlatform.entity;

import com.example.ELearningPlatform.model.EnrollmentStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "enrollments")
@Getter
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id")
    private Long enrollmentId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @Setter
    private EnrollmentStatus status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grade_id")
    @Setter(AccessLevel.PACKAGE)
    private Grade grade;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "progress_id")
    @Setter(AccessLevel.MODULE)
    private Progress progress;

    @Column(name = "identify_id", nullable = false, unique = true, updatable = false)
    private String identifyId;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Enrollment() {
        this.identifyId = UUID.randomUUID().toString();
    }

    public void setStudent(Student student) {
        this.student = student;
        student.addEnrollment(this);
    }

    public void setCourse(Course course) {
        this.course = course;
        course.addEnrollment(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Enrollment that = (Enrollment) o;
        return Objects.equals(identifyId, that.identifyId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(identifyId);
    }
}
