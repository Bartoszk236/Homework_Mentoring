package com.example.ELearningPlatform.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "answer_type")
@Getter
public abstract class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long answerId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "identify_id", nullable = false, unique = true, updatable = false)
    private String identifyId;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Answer() {
        this.identifyId = UUID.randomUUID().toString();
    }

    public abstract String getType();

    public void setQuestion(Question question) {
        this.question = question;
        question.addAnswer(this);
    }

    public void setStudent(Student student) {
        this.student = student;
        student.addAnswer(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return Objects.equals(identifyId, answer.identifyId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(identifyId);
    }
}
