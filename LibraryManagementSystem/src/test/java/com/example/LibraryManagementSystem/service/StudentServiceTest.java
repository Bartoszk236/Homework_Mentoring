package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.SampleDataBuilder;
import com.example.LibraryManagementSystem.entity.Student;
import com.example.LibraryManagementSystem.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@Import({
        StudentService.class,
        SampleDataBuilder.class
})
class StudentServiceTest {
    @Autowired
    private StudentService studentService;
    @Autowired
    private SampleDataBuilder sampleDataBuilder;
    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        sampleDataBuilder.initSampleData();
    }

    @Test
    void givenStudentWithBorrowsWhenGetStudentsDescCountOfBorrowsThenReturnPageWithSortByCountOfBorrows() {
        //given
        List<Student> students = studentRepository.findAll();

        List<Student> expectedOrder = students.stream()
                .sorted(Comparator.comparing((Student student) -> student.getBorrowRecords().size()).reversed()
                        .thenComparing(Student::getId))
                .limit(5)
                .toList();

        //when
        Page<Student> result = studentService.getStudentsDescCountOfBorrows(0, 5);

        //then
        assertThat(result).isNotEmpty();
        assertThat(result).hasSizeLessThanOrEqualTo(5);
        assertThat(result.getContent()).containsExactlyElementsOf(expectedOrder);
    }
}
