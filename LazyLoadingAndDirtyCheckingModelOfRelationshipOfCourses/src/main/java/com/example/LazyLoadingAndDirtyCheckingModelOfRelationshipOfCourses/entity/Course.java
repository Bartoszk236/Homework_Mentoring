package com.example.LazyLoadingAndDirtyCheckingModelOfRelationshipOfCourses.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Set<Student> students;
}
