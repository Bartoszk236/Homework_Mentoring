package com.example.ELearningPlatform.entity;

import com.example.ELearningPlatform.entity.embedded.Address;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "students")
@Getter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "first_name")
    @Setter
    private String firstName;

    @Column(name = "last_name")
    @Setter
    private String lastName;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "student")
    private Set<Answer> answer = new HashSet<>();

    @OneToMany(mappedBy = "student")
    private Set<Enrollment> enrollments = new HashSet<>();

    @Column(name = "identify_id", nullable = false, unique = true, updatable = false)
    private String identifyId;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Student() {
        this.identifyId = UUID.randomUUID().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(identifyId, student.identifyId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(identifyId);
    }

    void addEnrollment(Enrollment enrollment) {
        this.enrollments.add(enrollment);
    }

    void addAnswer(Answer answer) {
        this.answer.add(answer);
    }
}
