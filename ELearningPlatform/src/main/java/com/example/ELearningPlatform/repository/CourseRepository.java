package com.example.ELearningPlatform.repository;

import com.example.ELearningPlatform.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends
        JpaRepository<Course, Long>,
        QueryByExampleExecutor<Course>,
        JpaSpecificationExecutor<Course> {
}
