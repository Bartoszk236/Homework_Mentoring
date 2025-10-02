package com.example.ELearningPlatform;

import com.example.ELearningPlatform.entity.*;
import com.example.ELearningPlatform.model.CourseLevel;
import com.example.ELearningPlatform.model.EnrollmentStatus;
import com.github.javafaker.Faker;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.example.ELearningPlatform.model.CourseLevel.*;
import static com.example.ELearningPlatform.model.EnrollmentStatus.*;

@Component
public class SampleDataUtils {
    @Autowired
    private EntityManager em;
    private final Faker faker = new Faker();

    /*
    10x courses
    10x students
    every course have from 1 to 10 count of Reviews
    every course have from 1 to 15 count of Lessons
    every course have from 1 to 5 count of Enrollments
    every Enrollment have Progress
    every Progress have less or equal count of completedLessons then totalLessons count in Course
    */
    public void initSampleData() {
        List<Course> courses = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Course course = createCourse();
            courses.add(course);
            em.persist(course);
        }

        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Student student = createStudent();
            students.add(student);
            em.persist(student);
        }

        for (Course course : courses) {
            int randomCountOfReviews = faker.number().numberBetween(1, 10);
            int randomCountOfLessons = faker.number().numberBetween(1, 15);
            int randomCountOfEnrollments = faker.number().numberBetween(1, 5);

            for (int j = 0; j < randomCountOfReviews; j++) {
                em.persist(createReview(course));
            }

            for (int j = 0; j < randomCountOfLessons; j++) {
                em.persist(createLesson(course));
            }

            List<Integer> wornOutIndexes = new ArrayList<>();
            for (int j = 0; j < randomCountOfEnrollments; j++) {
                int rollStudentToEnrollment = faker.number().numberBetween(0, 9);
                while (wornOutIndexes.contains(rollStudentToEnrollment)) {
                    rollStudentToEnrollment = faker.number().numberBetween(0, 9);
                }
                wornOutIndexes.add(rollStudentToEnrollment);

                Enrollment enrollment = createEnrollment(course, students.get(rollStudentToEnrollment));
                em.persist(enrollment);

                int totalLessons = course.getLessons().size();
                em.persist(createProgress(enrollment, totalLessons));
            }
        }
        em.flush();
        em.clear();
    }

    private Course createCourse() {
        Course course = new Course();
        course.setName(faker.regexify("[a-zA-Z]{10}"));
        course.setCategory(faker.regexify("[a-zA-Z]{10}"));
        course.setCourseLevel(rollCourseLevel());
        return course;
    }

    private Review createReview(Course course) {
        Review review = new Review();
        review.setRate(faker.number().numberBetween(1, 5));
        review.setCourse(course);
        return review;
    }

    private Enrollment createEnrollment(Course course, Student student) {
        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setStatus(rollEnrollmentStatus());
        enrollment.setStudent(student);
        return enrollment;
    }

    private Student createStudent() {
        Student student = new Student();
        student.setFirstName(faker.name().firstName());
        student.setLastName(faker.name().lastName());
        return student;
    }

    private Lesson createLesson(Course course) {
        Lesson lesson = new Lesson();
        lesson.setCourse(course);
        return lesson;
    }

    private Progress createProgress(Enrollment enrollment, int maxCompletedLessons) {
        Progress progress = new Progress();
        progress.setCompletedLessons(faker.number().numberBetween(1, maxCompletedLessons));
        progress.setEnrollment(enrollment);
        return progress;
    }

    private CourseLevel rollCourseLevel() {
        List<CourseLevel> courseLevels = List.of(EASY, MEDIUM, HARD);
        return courseLevels.get(faker.number().numberBetween(0, 2));
    }

    private EnrollmentStatus rollEnrollmentStatus() {
        List<EnrollmentStatus> enrollmentStatuses = List.of(IN_PROGRESS, COMPLETED);
        return enrollmentStatuses.get(faker.number().numberBetween(0, 1));
    }
}
