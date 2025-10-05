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
@Table(name = "quizzes")
@Getter
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private Long quizId;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.MERGE)
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @OneToMany(mappedBy = "quiz")
    private Set<Question> questions = new HashSet<>();

    @Column(name = "identify_id", nullable = false, unique = true, updatable = false)
    private String identifyId = UUID.randomUUID().toString();

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
        lesson.addQuiz(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Quiz quiz = (Quiz) o;
        return Objects.equals(identifyId, quiz.identifyId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(identifyId);
    }

    void addQuestion(Question question) {
        this.questions.add(question);
    }
}
