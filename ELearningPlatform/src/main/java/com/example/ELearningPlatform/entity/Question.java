package com.example.ELearningPlatform.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "question_type")
@Getter
public abstract class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "text")
    private String text;

    @Column(name = "duration", precision = 5, scale = 2)
    private BigDecimal duration;

    @OneToMany(mappedBy = "question")
    private Set<Answer> answers = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @Column(name = "identify_id", nullable = false, unique = true, updatable = false)
    private String identifyId = UUID.randomUUID().toString();

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public abstract String getType();

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
        quiz.addQuestion(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(identifyId, question.identifyId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(identifyId);
    }

    void addAnswer(Answer answer) {
        answers.add(answer);
    }
}
