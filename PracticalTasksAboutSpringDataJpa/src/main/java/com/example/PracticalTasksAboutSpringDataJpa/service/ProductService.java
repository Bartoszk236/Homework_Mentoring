package com.example.PracticalTasksAboutSpringDataJpa.service;

import com.example.PracticalTasksAboutSpringDataJpa.entity.Product;
import com.example.PracticalTasksAboutSpringDataJpa.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.hibernate.query.SortDirection.*;

@Service
@RequiredArgsConstructor
public class ProductService {
    private static final List<String> ALLOWS_FIELDS = List.of("name", "category", "price", "createdDate");
    private final ProductRepository productRepository;

    public List<Product> findProducts(String sortBy, SortDirection sortDirection) {
        if (!ALLOWS_FIELDS.contains(sortBy)) throw new IllegalArgumentException();

        Sort sort;
        if (sortDirection == DESCENDING) {
            sort = Sort.by(Sort.Direction.DESC, sortBy);
        } else {
            sort = Sort.by(Sort.Direction.ASC, sortBy);
        }
        return productRepository.findAll(sort);
    }
}
