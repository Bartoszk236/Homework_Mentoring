package com.example.ELearningPlatform.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "progress")
@Getter
public class Progress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "progress_id")
    private Long progressId;

    @Column(name = "completed_lessons")
    @Setter
    private Integer completedLessons;

    @OneToOne(mappedBy = "progress", cascade = CascadeType.MERGE)
    private Enrollment enrollment;

    @Column(name = "identify_id", nullable = false, unique = true, updatable = false)
    private String identifyId;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Progress() {
        this.identifyId = UUID.randomUUID().toString();
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
        enrollment.setProgress(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Progress progress = (Progress) o;
        return Objects.equals(identifyId, progress.identifyId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(identifyId);
    }
}
