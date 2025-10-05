package com.example.ELearningPlatform.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "grades")
@Getter
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_id")
    private Long gradeId;

    @Column(name = "grade")
    private Double grade;

    @OneToOne(mappedBy = "grade", cascade = CascadeType.MERGE)
    private Enrollment enrollment;

    @Column(name = "identify_id", nullable = false, unique = true, updatable = false)
    private String identifyId = UUID.randomUUID().toString();

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
        enrollment.setGrade(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Grade grade = (Grade) o;
        return Objects.equals(identifyId, grade.identifyId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(identifyId);
    }
}
