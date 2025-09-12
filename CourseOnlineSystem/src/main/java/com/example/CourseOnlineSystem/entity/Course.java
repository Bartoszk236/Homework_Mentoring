package com.example.CourseOnlineSystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity
@Table(name = "courses")
@Getter
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Integer id;

    @Setter
    @Column(name = "title")
    private String title;

    @Setter
    @Column(name = "description")
    private String description;

    @Setter
    @Column(name = "credits")
    private Integer credits;

    @Setter
    @Column(name = "course_code", unique = true)
    private String courseCode;

    @OneToMany(mappedBy = "course")
    private Set<Enrollment> enrollments = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(courseCode, course.courseCode);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(courseCode);
    }
}
