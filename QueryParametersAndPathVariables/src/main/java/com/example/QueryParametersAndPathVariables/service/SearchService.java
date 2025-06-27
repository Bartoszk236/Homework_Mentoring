package com.example.QueryParametersAndPathVariables.service;

import com.example.QueryParametersAndPathVariables.entity.Product;
import com.example.QueryParametersAndPathVariables.model.Category;
import com.example.QueryParametersAndPathVariables.model.SortBy;
import com.example.QueryParametersAndPathVariables.model.Subcategory;
import com.example.QueryParametersAndPathVariables.repository.ProductRepository;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final ProductRepository productRepository;

    public Page<Product> search(Category category, BigDecimal minPrice, BigDecimal maxPrice, List<String> tags, SortBy sortBy, @Min(0) int page, @Min(1) int size) {
        Sort sort = Sort.by(Sort.Order.asc(sortString(sortBy)));
        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Product> specification = hasCategory(category)
        .and(priceGte(minPrice))
        .and(priceLte(maxPrice))
        .and(hasTags(tags));

        return productRepository.findAll(specification, pageable);
    }

    public Page<Product> search(Category category, Subcategory subcategory, Map<String, String> filters) {
        BigDecimal minPrice = filters.containsKey("minPrice")
                ? new BigDecimal(filters.get("minPrice"))
                : null;

        BigDecimal maxPrice = filters.containsKey("maxPrice")
                ? new BigDecimal(filters.get("maxPrice"))
                : null;

        List<String> tags = filters.containsKey("tag")
                ? getTags(filters)
                : null;

        String sortString = filters.getOrDefault("sortBy", "id");

        int page = filters.containsKey("page")
                ? Integer.parseInt(filters.get("page"))
                : 0;

        int size = filters.containsKey("size")
                ? Integer.parseInt(filters.get("size"))
                : 5;

        Sort sort = Sort.by(Sort.Order.asc(sortString));
        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Product> specification = hasCategory(category)
                .and(hasSubcategory(subcategory))
                .and(priceGte(minPrice))
                .and(priceLte(maxPrice))
                .and(hasTags(tags));

        return productRepository.findAll(specification, pageable);
    }

    private String sortString(SortBy sortBy) {
        if (sortBy == null) return "id";
        return switch (sortBy) {
            case NAME -> "name";
            case PRICE -> "price";
            case DATE -> "createdOn";
        };
    }

    private Specification<Product> hasCategory(Category category) {
        return (root, query, cb) ->
                cb.equal(root.get("category"), category);
    }

    private Specification<Product> hasSubcategory(Subcategory subcategory) {
        return (root, query, cb) ->
                cb.equal(root.get("subcategory"), subcategory);
    }

    private Specification<Product> priceGte(BigDecimal min) {
        return (min == null)
                ? null
                : (root, query, cb) ->
                cb.greaterThanOrEqualTo(root.get("price"), min);
    }

    private Specification<Product> priceLte(BigDecimal max) {
        return (max == null)
                ? null
                : (root, query, cb) ->
                cb.lessThanOrEqualTo(root.get("price"), max);
    }

    private Specification<Product> hasTags(List<String> tags) {
        return (tags == null || tags.isEmpty())
                ? null
                : (root, query, cb) ->
                root.get("tag").in(tags);
    }

    private List<String> getTags(Map<String, String> filters) {
        List<String> tags = new ArrayList<>();
        filters.forEach((key, value) -> {
            if (key.equals("tag")) tags.add(value);
        });
        return tags;
    }
}

