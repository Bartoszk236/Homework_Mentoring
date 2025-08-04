package com.example.StreamingAndPerformanceOptimization.service;

import com.example.StreamingAndPerformanceOptimization.entity.Users;
import com.example.StreamingAndPerformanceOptimization.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public Stream<List<Users>> streamAllUsers(int batchSize, String filter) {
        Specification<Users> spec = buildFilterSpecification(filter);
        Iterator<List<Users>> iterator = new Iterator<>() {
            private int page = 0;
            private List<Users> nextBatch = fetchPage();

            private List<Users> fetchPage() {
                PageRequest pr = PageRequest.of(page, batchSize);
                Page<Users> p = userRepository.findAll(spec, pr);
                return p.getContent();
            }

            @Override
            public boolean hasNext() {
                return !nextBatch.isEmpty();
            }

            @Override
            public List<Users> next() {
                List<Users> current = nextBatch;
                page++;
                nextBatch = fetchPage();
                return current;
            }
        };

        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED),
                false
        );
    }

    private Specification<Users> buildFilterSpecification(String filter) {
        if (filter == null || filter.isBlank()) {
            return null;
        }
        return (root, query, cb) -> {
            String like = "%" + filter.toLowerCase() + "%";
            return cb.or(
                    cb.like(root.get("id").as(String.class), like),
                    cb.like(cb.lower(root.get("username")), like)
            );
        };
    }
}
