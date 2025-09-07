package com.example.CourseOnlineSystem.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "courses")
@Getter
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Integer id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "credits")
    private Integer credits;
    @Column(name = "course_code", unique = true)
    private String courseCode;
    @ManyToMany(mappedBy = "enrolledCourses")
    private Set<Student> students = new HashSet<>();

    void addStudent(Student student) {
        students.add(student);
    }

    void removeStudent(Student student) {
        students.remove(student);
    }

    public Course setCourseCode(String courseCode) {
        this.courseCode = courseCode;
        return this;
    }

    public Course setCredits(Integer credits) {
        this.credits = credits;
        return this;
    }

    public Course setDescription(String description) {
        this.description = description;
        return this;
    }

    public Course setTitle(String title) {
        this.title = title;
        return this;
    }

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
