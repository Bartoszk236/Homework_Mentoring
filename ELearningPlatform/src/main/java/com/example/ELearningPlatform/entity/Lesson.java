package com.example.ELearningPlatform.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "lessons")
@Getter
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    private Long lessonId;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "lesson")
    private Set<Quiz> quizzes = new HashSet<>();

    @Column(name = "identify_id", nullable = false, unique = true, updatable = false)
    private String identifyId;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Lesson() {
        this.identifyId = UUID.randomUUID().toString();
    }

    public void setCourse(Course course) {
        this.course = course;
        course.addLesson(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return Objects.equals(identifyId, lesson.identifyId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(identifyId);
    }

    void addQuiz(Quiz quiz) {
        quizzes.add(quiz);
    }
}
