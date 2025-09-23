package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.dto.CategoryWithAvgPages;
import com.example.LibraryManagementSystem.entity.Book;
import com.example.LibraryManagementSystem.entity.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @PersistenceContext
    private EntityManager em;

    public List<CategoryWithAvgPages> getCategoriesWithAvgPages() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        Root<Category> root = query.from(Category.class);

        Join<Category, Book> bookJoin = root.join("books", JoinType.LEFT);
        Expression<Double> avgPages = cb.avg(bookJoin.get("pagesNumber"));
        query.multiselect(root, avgPages)
                .groupBy(root);

        return em.createQuery(query).getResultList()
                .stream()
                .map(result -> new CategoryWithAvgPages((Category) result[0], (Double) result[1]))
                .toList();
    }
}
