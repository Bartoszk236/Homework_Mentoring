package com.example.ELearningPlatform.service;

import com.example.ELearningPlatform.dto.CourseSearchRequest;
import com.example.ELearningPlatform.entity.Course;
import com.example.ELearningPlatform.entity.Enrollment;
import com.example.ELearningPlatform.entity.Review;
import com.example.ELearningPlatform.model.EnrollmentStatus;
import com.example.ELearningPlatform.repository.CourseRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    @PersistenceContext
    private EntityManager em;

    public List<Course> findCoursesAdvanced(CourseSearchRequest request) {
        Course probe = new Course();
        if (request.name() != null) probe.setName(request.name());
        if (request.category() != null) probe.setCategory(request.category());
        if (request.level() != null) probe.setCourseLevel(request.level());

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("courseLevel", ExampleMatcher.GenericPropertyMatcher::exact)
                .withIgnoreCase(true)
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreNullValues()
                .withIgnorePaths("identifyId");

        Example<Course> example = Example.of(probe, matcher);
        Specification<Course> qbeSpec = (root, query, cb) ->
                QueryByExamplePredicateBuilder.getPredicate(root, cb, example);

        Specification<Course> rateAvgSpec = (root, query, cb) -> {
           if (request.minAverageRating() == null) return cb.conjunction();

           Subquery<Double> sq = query.subquery(Double.class);
           Root<Review> review = sq.from(Review.class);
           sq.select(cb.avg(review.get("rate")))
                   .where(cb.equal(review.get("course"), root));

           return cb.greaterThanOrEqualTo(sq, request.minAverageRating());
        };

        Specification<Course> enrollmentCountSpec = (root, query, cb) -> {
            if (request.minEnrollmentCount() == null) return cb.conjunction();

            Subquery<Long> sq = query.subquery(Long.class);
            Root<Enrollment> enrollment = sq.from(Enrollment.class);
            sq.select(cb.count(enrollment))
                    .where(cb.equal(enrollment.get("course"), root));

            return cb.greaterThanOrEqualTo(sq, request.minEnrollmentCount());
        };

        Specification<Course> specification = Specification.allOf(rateAvgSpec, enrollmentCountSpec,  qbeSpec);

        return courseRepository.findAll(specification);
    }

    public List<Course> getCoursesOrderByCompletedRateDesc() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Course> query = cb.createQuery(Course.class);
        Root<Course> course = query.from(Course.class);

        Join<Course, Enrollment> enrollmentJoin = course.join("enrollments", JoinType.LEFT);

        Expression<Double> score = cb.<Double>selectCase()
                .when(cb.equal(enrollmentJoin.get("status"), EnrollmentStatus.COMPLETED), 1.0)
                .when(cb.equal(enrollmentJoin.get("status"), EnrollmentStatus.IN_PROGRESS), 0.0)
                .otherwise(0.0);

        Expression<Double> rate = cb.avg(score);
        query.select(course)
                .groupBy(course)
                .orderBy(cb.desc(rate));

        return em.createQuery(query).getResultList();
    }
}
