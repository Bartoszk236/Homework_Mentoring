package com.example.ELearningPlatform.service;

import com.example.ELearningPlatform.SampleDataUtils;
import com.example.ELearningPlatform.dto.CourseSearchRequest;
import com.example.ELearningPlatform.entity.Course;
import com.example.ELearningPlatform.entity.Enrollment;
import com.example.ELearningPlatform.entity.Review;
import com.example.ELearningPlatform.model.CourseLevel;
import com.example.ELearningPlatform.model.EnrollmentStatus;
import com.example.ELearningPlatform.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import javax.swing.*;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@Import({CourseService.class, SampleDataUtils.class})
class CourseServiceTest {
    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private SampleDataUtils utils;

    @BeforeEach
    void setUp() {
        utils.initSampleData();
    }

    @Test
    void givenCourseNameWhenFindCoursesAdvancedThenReturnListBooksContainingGivenName() {
        //given
        String givenCourseName = courseRepository.findAll().stream()
                .findFirst()
                .orElseThrow()
                .getName();

        CourseSearchRequest request = new CourseSearchRequest(givenCourseName, null, null, null, null);

        //when
        List<Course> results = courseService.findCoursesAdvanced(request);

        //then
        assertThat(results).isNotEmpty();
        results.stream()
                .map(Course::getName)
                .forEach(name -> assertThat(name).contains(givenCourseName));
    }

    @Test
    void givenCategoryCourseWhenFindCoursesAdvancedThenReturnList() {
        //given
        String givenCategoryName = courseRepository.findAll().stream()
                .findFirst()
                .orElseThrow()
                .getCategory();

        CourseSearchRequest request = new CourseSearchRequest(null, givenCategoryName, null, null, null);

        //when
        List<Course> results = courseService.findCoursesAdvanced(request);

        //then
        assertThat(results).isNotEmpty();
        results.stream()
                .map(Course::getCategory)
                .forEach(categoryName -> assertThat(categoryName).contains(givenCategoryName));
    }

    @Test
    void givenCourseLevelWhenFindCoursesAdvancedThenReturnList() {
        //given
        CourseLevel givenLevel = courseRepository.findAll().stream()
                .findFirst()
                .orElseThrow()
                .getCourseLevel();

        CourseSearchRequest request = new CourseSearchRequest(null, null, givenLevel, null, null);

        //when
        List<Course> results = courseService.findCoursesAdvanced(request);

        //then
        assertThat(results).isNotEmpty();
        results.stream()
                .map(Course::getCourseLevel)
                .forEach(level -> assertThat(level).isEqualTo(givenLevel));
    }

    @Test
    void givenMinAverageRatingWhenFindCoursesAdvancedThenReturnList() {
        //given
        Double givenMinAverageRating = 2.0;

        CourseSearchRequest request = new CourseSearchRequest(null, null, null, givenMinAverageRating, null);

        //when
        List<Course> results = courseService.findCoursesAdvanced(request);

        //then
        assertThat(results).isNotEmpty();
        results.forEach(course -> {
            Set<Review> reviews = course.getReviews();
            Integer sum = reviews.stream()
                    .map(Review::getRate)
                    .reduce(Integer::sum)
                    .orElse(0);
            Double avg = Double.valueOf(sum) / reviews.size();
            assertThat(avg).isGreaterThanOrEqualTo(givenMinAverageRating);
        });
    }

    @Test
    void givenMinEnrollmentCountWhenFindCoursesAdvancedThenReturnList() {
        //given
        Long givenMinEnrollmentCount = 2L;

        CourseSearchRequest request = new CourseSearchRequest(null, null, null, null, givenMinEnrollmentCount);

        //when
        List<Course> results = courseService.findCoursesAdvanced(request);

        //then
        assertThat(results).isNotEmpty();
        results.forEach(course -> {
            Long enrollmentsSize = (long) course.getEnrollments().size();
            assertThat(enrollmentsSize).isGreaterThanOrEqualTo(givenMinEnrollmentCount);
        });
    }

    @Test
    void givenSampleDataWhenGetCoursesOrderByCompletedRateDescThenReturnList() {
        //when
        List<Course> results = courseService.getCoursesOrderByCompletedRateDesc();

        //then
        assertThat(results).isNotEmpty();

        List<Course> expectedOrder = results.stream()
                .sorted(Comparator.comparing(this::calculateCompletedRate).reversed()
                        .thenComparing(Course::getCourseId).reversed()
                )
                .toList();

        assertThat(results).containsExactlyElementsOf(expectedOrder);
    }

    private Double calculateCompletedRate(Course course) {
        Set<Enrollment> enrollments = course.getEnrollments();
        int size = enrollments.size();
        if (size == 0) return 0.0;

        List<EnrollmentStatus> statuses = enrollments.stream()
                .map(Enrollment::getStatus)
                .toList();

        long sum = statuses.stream()
                .filter(status -> status.equals(EnrollmentStatus.COMPLETED))
                .count();

        return (double) sum / size;
    }
}
