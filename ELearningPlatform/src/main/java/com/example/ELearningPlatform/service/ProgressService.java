package com.example.ELearningPlatform.service;

import com.example.ELearningPlatform.dto.ProgressDataDto;
import com.example.ELearningPlatform.dto.ProgressStudentDto;
import com.example.ELearningPlatform.entity.*;
import com.example.ELearningPlatform.repository.CourseRepository;
import com.example.ELearningPlatform.repository.EnrollmentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgressService {
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    @PersistenceContext
    private EntityManager em;

    public List<ProgressStudentDto> getStudentsProgressByCourse(Long courseId) {
        if (!courseRepository.existsById(courseId))
            throw new IllegalArgumentException("not found course with id: " + courseId);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ProgressStudentDto> query = cb.createQuery(ProgressStudentDto.class);

        Root<Course> courseRoot = query.from(Course.class);
        Join<Course, Enrollment> enrollmentJoin = courseRoot.join("enrollments", JoinType.LEFT);
        Join<Enrollment, Student> studentJoin = enrollmentJoin.join("student", JoinType.LEFT);
        Join<Enrollment, Progress> progressJoin = enrollmentJoin.join("progress", JoinType.LEFT);
        Join<Course, Lesson> lessonJoin = courseRoot.join("lessons", JoinType.LEFT);

        Expression<Long> totalLessons = cb.countDistinct(lessonJoin.get("id"));
        Expression<Integer> completedLessons = progressJoin.get("completedLessons");
        Expression<String> fullName = cb.concat(
                cb.concat(studentJoin.get("firstName"), cb.literal(" ")),
                studentJoin.get("lastName"));

        query.select(cb.construct(
                ProgressStudentDto.class,
                fullName,
                completedLessons,
                totalLessons
        ));

        query.where(cb.equal(courseRoot.get("courseId"), courseId));
        query.groupBy(
                studentJoin.get("studentId"),
                studentJoin.get("firstName"),
                studentJoin.get("lastName"),
                completedLessons

        );
        return em.createQuery(query).getResultList();
    }

    public ProgressDataDto getProgressDataByStudentAndCourse(Long studentId, Long courseId) {
        if (!enrollmentRepository.existsByStudent_StudentIdAndCourse_CourseId(studentId, courseId))
            throw new IllegalArgumentException("enrollment for student " + studentId + " and course " + courseId + " not found");

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ProgressDataDto> query = cb.createQuery(ProgressDataDto.class);

        Root<Enrollment> enrollmentRoot = query.from(Enrollment.class);

        Subquery<Long> totalLessonSubquery = query.subquery(Long.class);
        Root<Lesson> lessonRoot = totalLessonSubquery.from(Lesson.class);

        totalLessonSubquery
                .select(cb.count(lessonRoot))
                .where(cb.equal(lessonRoot.get("course").get("courseId"), courseId));

        Subquery<Integer> completedLessonSubquery = query.subquery(Integer.class);
        Root<Progress> progressRoot = completedLessonSubquery.from(Progress.class);

        completedLessonSubquery
                .select(progressRoot.get("completedLessons"))
                .where(cb.equal(progressRoot.get("enrollment"), enrollmentRoot));

        query.select(cb.construct(
                ProgressDataDto.class,
                totalLessonSubquery,
                completedLessonSubquery
        ));

        query.where(cb.and(
                        cb.equal(enrollmentRoot.get("student").get("studentId"), studentId)),
                cb.equal(enrollmentRoot.get("course").get("courseId"), courseId));

        return em.createQuery(query).getSingleResult();
    }
}
