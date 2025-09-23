package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.entity.BorrowRecord;
import com.example.LibraryManagementSystem.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@Import(StudentService.class)
class StudentServiceTest {
    @Autowired
    private StudentService studentService;
    @Autowired
    private TestEntityManager em;

    @Test
    void givenStudentWithBorrowsWhenGetStudentsDescCountOfBorrowsThenReturnPageWithSortByCountOfBorrows() {
        //given
        Student student1 = new Student();
        student1.setName("Student1");
        em.persist(student1);

        Student student2 = new Student();
        student2.setName("Student2");
        em.persist(student2);

        Student student3 = new Student();
        student3.setName("Student3");
        em.persist(student3);

        Student student4 = new Student();
        student4.setName("Student4");
        em.persist(student4);

        BorrowRecord borrowRecord1 = new BorrowRecord();
        borrowRecord1.setBorrowDate(LocalDate.now().minusDays(1));
        borrowRecord1.setStudent(student4);
        em.persist(borrowRecord1);

        BorrowRecord borrowRecord2 = new BorrowRecord();
        borrowRecord2.setBorrowDate(LocalDate.now().minusDays(2));
        borrowRecord2.setStudent(student3);
        em.persist(borrowRecord2);

        BorrowRecord borrowRecord3 = new BorrowRecord();
        borrowRecord3.setBorrowDate(LocalDate.now().minusDays(3));
        borrowRecord3.setStudent(student3);
        em.persist(borrowRecord3);

        BorrowRecord borrowRecord4 = new BorrowRecord();
        borrowRecord4.setBorrowDate(LocalDate.now().minusDays(4));
        borrowRecord4.setStudent(student2);
        em.persist(borrowRecord4);

        BorrowRecord borrowRecord5 = new BorrowRecord();
        borrowRecord5.setBorrowDate(LocalDate.now().minusDays(5));
        borrowRecord5.setStudent(student2);
        em.persist(borrowRecord5);

        BorrowRecord borrowRecord6 = new BorrowRecord();
        borrowRecord6.setBorrowDate(LocalDate.now().minusDays(6));
        borrowRecord6.setStudent(student2);
        em.persist(borrowRecord6);

        em.flush();
        em.clear();

        //when
        Page<Student> result = studentService.getStudentsDescCountOfBorrows(0, 4);

        //then
        assertThat(result).hasSize(4);
        assertThat(result.getContent()).containsExactly(student2, student3, student4, student1);
    }
}
