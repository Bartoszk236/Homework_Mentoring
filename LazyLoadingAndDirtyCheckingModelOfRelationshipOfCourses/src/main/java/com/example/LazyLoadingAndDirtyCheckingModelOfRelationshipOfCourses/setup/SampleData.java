package com.example.LazyLoadingAndDirtyCheckingModelOfRelationshipOfCourses.setup;

import com.example.LazyLoadingAndDirtyCheckingModelOfRelationshipOfCourses.entity.Course;
import com.example.LazyLoadingAndDirtyCheckingModelOfRelationshipOfCourses.entity.Student;
import com.example.LazyLoadingAndDirtyCheckingModelOfRelationshipOfCourses.repository.CourseRepository;
import com.example.LazyLoadingAndDirtyCheckingModelOfRelationshipOfCourses.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class SampleData implements ApplicationRunner {
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    @Override
    public void run(ApplicationArguments args) {
        Student student1 = Student.builder()
                .firstName("John1")
                .lastName("Doe1")
                .build();

        Student student2 = Student.builder()
                .firstName("John2")
                .lastName("Doe2")
                .build();

        Student student3 = Student.builder()
                .firstName("John3")
                .lastName("Doe3")
                .build();

        Student student4 = Student.builder()
                .firstName("John4")
                .lastName("Doe4")
                .build();

        Student student5 = Student.builder()
                .firstName("John5")
                .lastName("Doe5")
                .build();

        Student student6 = Student.builder()
                .firstName("John6")
                .lastName("Doe6")
                .build();

        Student student7 = Student.builder()
                .firstName("John7")
                .lastName("Doe7")
                .build();

        Student student8 = Student.builder()
                .firstName("John8")
                .lastName("Doe8")
                .build();

        Student student9 = Student.builder()
                .firstName("John9")
                .lastName("Doe9")
                .build();

        Student student10 = Student.builder()
                .firstName("John10")
                .lastName("Doe10")
                .build();

        Course course1 = Course.builder()
                .name("Course1")
                .description("Course1 description")
                .students(Set.of(student1, student2, student3, student4))
                .build();

        Course course2 = Course.builder()
                .name("Course 2")
                .description("Course2 description")
                .students(Set.of(student5, student6, student7))
                .build();

        Course course3 = Course.builder()
                .name("Course3")
                .description("Course 3 description")
                .students(Set.of(student8, student9, student10))
                .build();

        studentRepository.saveAll(List.of(student1, student2, student3, student4, student5, student6, student7, student8, student9, student10));
        courseRepository.saveAll(List.of(course1, course2, course3));
    }
}
