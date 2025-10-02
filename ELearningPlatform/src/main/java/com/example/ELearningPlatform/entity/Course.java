package com.example.ELearningPlatform.entity;

import com.example.ELearningPlatform.model.CourseLevel;
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
@Table(name = "courses")
@Getter
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "name")
    @Setter
    private String name;

    @Column(name = "course_level")
    @Setter
    private CourseLevel courseLevel;

    @Column(name = "category")
    @Setter
    private String category;

    @OneToMany(mappedBy = "course")
    private Set<Review> reviews = new HashSet<>();

    @OneToMany(mappedBy = "course")
    private Set<Lesson> lessons = new HashSet<>();

    @OneToMany(mappedBy = "course")
    private Set<Enrollment> enrollments = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "course_tags",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"course_id", "tag_id"})
    )
    private Set<Tag> tags = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "course_prerequisites",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "prerequisite_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"course_id", "prerequisite_id"})
    )
    private Set<Course> prerequisites = new HashSet<>();

    @ManyToMany(mappedBy = "prerequisites", fetch = FetchType.LAZY)
    private Set<Course> dependents = new HashSet<>();

    @Column(name = "identify_id", nullable = false, unique = true, updatable = false)
    private String identifyId = UUID.randomUUID().toString();

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void addTag(Tag tag) {
        this.tags.add(tag);
        tag.addCourse(this);
    }

    public void addPrerequisite(Course course) {
        this.prerequisites.add(course);
        this.dependents.add(course);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(identifyId, course.identifyId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(identifyId);
    }

    void addEnrollment(Enrollment enrollment) {
        this.enrollments.add(enrollment);
    }

    void addLesson(Lesson lesson) {
        this.lessons.add(lesson);
    }

    void addOpinion(Review review) {
        this.reviews.add(review);
    }
}
