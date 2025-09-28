package com.example.ELearningPlatform.service;

import com.example.ELearningPlatform.dto.CourseSearchRequest;
import com.example.ELearningPlatform.entity.Course;
import com.example.ELearningPlatform.entity.Enrollment;
import com.example.ELearningPlatform.entity.Review;
import com.example.ELearningPlatform.model.CourseLevel;
import com.example.ELearningPlatform.model.EnrollmentStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@Import(CourseService.class)
class CourseServiceTest {
    @Autowired
    private CourseService courseService;
    @Autowired
    private TestEntityManager em;

    @Test
    void givenCourseNameWhenFindCoursesAdvancedThenReturnList() {
        //given
        String givenCourseName = "test";

        Course course = new Course();
        course.setName(givenCourseName);
        em.persist(course);

        Course course2 = new Course();
        course2.setName("test2");
        em.persist(course2);

        Course course3 = new Course();
        course3.setName("new course");
        em.persist(course3);

        em.flush();
        em.clear();

        CourseSearchRequest request = new CourseSearchRequest(givenCourseName, null, null, null, null);

        //when
        List<Course> results = courseService.findCoursesAdvanced(request);

        //then
        assertThat(results).hasSize(2);
        assertThat(results).containsExactlyInAnyOrderElementsOf(List.of(course, course2));
    }

    @Test
    void givenCategoryCourseWhenFindCoursesAdvancedThenReturnList() {
        //given
        String givenCategory = "test";
        Course course = new Course();
        course.setCategory(givenCategory);
        em.persist(course);

        Course course2 = new Course();
        course2.setCategory("another");
        em.persist(course2);

        em.flush();
        em.clear();

        CourseSearchRequest request = new CourseSearchRequest(null, givenCategory, null, null, null);

        //when
        List<Course> results = courseService.findCoursesAdvanced(request);

        //then
        assertThat(results).hasSize(1);
        assertThat(results).containsExactlyInAnyOrderElementsOf(List.of(course));
    }

    @Test
    void givenCourseLevelWhenFindCoursesAdvancedThenReturnList() {
        //given
        CourseLevel givenLevel = CourseLevel.EASY;

        Course course = new Course();
        course.setCourseLevel(givenLevel);
        em.persist(course);

        Course course2 = new Course();
        course2.setCourseLevel(CourseLevel.HARD);
        em.persist(course2);

        em.flush();
        em.clear();

        CourseSearchRequest request = new CourseSearchRequest(null, null, givenLevel, null, null);

        //when
        List<Course> results = courseService.findCoursesAdvanced(request);

        //then
        assertThat(results).hasSize(1);
        assertThat(results).containsExactlyInAnyOrderElementsOf(List.of(course));
    }

    @Test
    void givenMinAverageRatingWhenFindCoursesAdvancedThenReturnList() {
        //given
        Double givenMinAverageRating = 4.5;

        Course course = new Course();
        em.persist(course);

        Course course2 = new Course();
        em.persist(course2);

        Review review = new Review();
        review.setRate(5);
        review.setCourse(course);
        em.persist(review);

        Review review2 = new Review();
        review2.setRate(4);
        review2.setCourse(course);
        em.persist(review2);

        Review review3 = new Review();
        review3.setRate(3);
        review3.setCourse(course2);
        em.persist(review3);

        em.flush();
        em.clear();

        CourseSearchRequest request = new CourseSearchRequest(null, null, null, givenMinAverageRating, null);

        //when
        List<Course> results = courseService.findCoursesAdvanced(request);

        //then
        assertThat(results).hasSize(1);
        assertThat(results).containsExactlyInAnyOrderElementsOf(List.of(course));
    }

    @Test
    void givenMinEnrollmentCountWhenFindCoursesAdvancedThenReturnList() {
        //given
        Long givenMinEnrollmentCount = 2L;

        Course course = new Course();
        em.persist(course);

        Course course2 = new Course();
        em.persist(course2);

        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        em.persist(enrollment);

        Enrollment enrollment2 = new Enrollment();
        enrollment2.setCourse(course);
        em.persist(enrollment2);

        Enrollment enrollment3 = new Enrollment();
        enrollment3.setCourse(course2);
        em.persist(enrollment3);

        em.flush();
        em.clear();

        CourseSearchRequest request = new CourseSearchRequest(null, null, null, null, givenMinEnrollmentCount);

        //when
        List<Course> results = courseService.findCoursesAdvanced(request);

        //then
        assertThat(results).hasSize(1);
        assertThat(results).containsExactlyInAnyOrderElementsOf(List.of(course));
    }

    @Test
    void test_getCoursesOrderByCompletedRateDesc() {
        //given
        Course course = new Course();
        em.persist(course);

        Course course2 = new Course();
        em.persist(course2);

        Course course3 = new Course();
        em.persist(course3);

        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course2);
        enrollment.setStatus(EnrollmentStatus.COMPLETED);
        em.persist(enrollment);

        Enrollment enrollment2 = new Enrollment();
        enrollment2.setCourse(course2);
        enrollment2.setStatus(EnrollmentStatus.COMPLETED);
        em.persist(enrollment2);

        Enrollment enrollment3 = new Enrollment();
        enrollment3.setCourse(course2);
        enrollment3.setStatus(EnrollmentStatus.COMPLETED);
        em.persist(enrollment3);

        Enrollment enrollment4 = new Enrollment();
        enrollment4.setCourse(course3);
        enrollment4.setStatus(EnrollmentStatus.COMPLETED);
        em.persist(enrollment4);

        Enrollment enrollment5 = new Enrollment();
        enrollment5.setCourse(course3);
        enrollment5.setStatus(EnrollmentStatus.COMPLETED);
        em.persist(enrollment5);

        Enrollment enrollment6 = new Enrollment();
        enrollment6.setCourse(course3);
        enrollment6.setStatus(EnrollmentStatus.IN_PROGRESS);
        em.persist(enrollment6);

        Enrollment enrollment7 = new Enrollment();
        enrollment7.setCourse(course);
        enrollment7.setStatus(EnrollmentStatus.COMPLETED);
        em.persist(enrollment7);

        Enrollment enrollment8 = new Enrollment();
        enrollment8.setCourse(course);
        enrollment8.setStatus(EnrollmentStatus.IN_PROGRESS);
        em.persist(enrollment8);

        Enrollment enrollment9 = new Enrollment();
        enrollment9.setCourse(course);
        enrollment9.setStatus(EnrollmentStatus.IN_PROGRESS);
        em.persist(enrollment9);

        em.flush();
        em.clear();

        //when
        List<Course> results = courseService.getCoursesOrderByCompletedRateDesc();

        //then
        assertThat(results).hasSize(3);
        assertThat(results).containsExactly(course2, course3, course);
    }
}
