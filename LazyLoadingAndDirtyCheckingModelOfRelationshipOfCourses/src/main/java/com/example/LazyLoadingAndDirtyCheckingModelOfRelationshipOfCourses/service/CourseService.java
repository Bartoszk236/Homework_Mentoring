package com.example.LazyLoadingAndDirtyCheckingModelOfRelationshipOfCourses.service;

import com.example.LazyLoadingAndDirtyCheckingModelOfRelationshipOfCourses.entity.Course;
import com.example.LazyLoadingAndDirtyCheckingModelOfRelationshipOfCourses.repository.CourseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseService {
    private final CourseRepository courseRepository;

    @Transactional
    public void lazyLoadTest() {
        log.debug("lazyLoadTest");
        Course course = courseRepository.findByStudent(1L).orElseThrow();
        log.debug("lazy load test course: {}", course.getName());

        log.debug("fetching students");
        course.getStudents().forEach(student -> log.debug(student.getFirstName()));
    }
}
