package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.entity.BorrowRecord;
import com.example.LibraryManagementSystem.entity.Student;
import com.example.LibraryManagementSystem.repository.StudentRepository;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    public Page<Student> getStudentsDescCountOfBorrows(int page, int pageSize) {
        Specification<Student> specification = (root, query, cb) -> {
            Join<Student, BorrowRecord> borrowRecordJoin = root.join("borrowRecords", JoinType.LEFT);
            Expression<Long> countOfBorrows = cb.count(borrowRecordJoin);

            query.where(cb.greaterThan(
                            borrowRecordJoin.get("borrowDate"),
                            LocalDate.now().minusMonths(6)
                    ))
                    .groupBy(root)
                    .orderBy(cb.desc(countOfBorrows));

            return cb.conjunction();
        };


        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return studentRepository.findAll(specification, pageRequest);
    }
}
