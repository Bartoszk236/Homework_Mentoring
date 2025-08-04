package com.example.QueryParametersAndPathVariables.service;

import com.example.QueryParametersAndPathVariables.entity.Product;
import com.example.QueryParametersAndPathVariables.model.Category;
import com.example.QueryParametersAndPathVariables.model.SortBy;
import com.example.QueryParametersAndPathVariables.model.Subcategory;
import com.example.QueryParametersAndPathVariables.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class SearchServiceTest {
    private final ProductRepository productRepository = mock(ProductRepository.class);
    private final SearchService searchService = new SearchService(productRepository);
    private final Product product = new Product();
    private final Page<Product> productPage = new PageImpl<>(List.of(product));

    @Test
    void givenValidParametersWhenSearchProductThenReturnPageOfProductsAndCallRepository() {
        //given
        Category category = Category.ELECTRONICS;
        BigDecimal minPrice = new BigDecimal("10");
        BigDecimal maxPrice = new BigDecimal("100");
        List<String> tags = List.of("tag1", "tag2", "tag3");
        int page = 1, size = 10;
        SortBy sortBy = SortBy.PRICE;

        when(productRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(productPage);

        //when
        Page<Product> result = searchService.search(category, minPrice, maxPrice, tags, sortBy, page, size);

        //then
        assertSame(productPage, result);

        @SuppressWarnings("unchecked")
        ArgumentCaptor<Specification<Product>> specificationArgumentCaptor = ArgumentCaptor.forClass(Specification.class);
        ArgumentCaptor<Pageable> pageableArgumentCaptor = ArgumentCaptor.forClass(Pageable.class);

        verify(productRepository, times(1))
                .findAll(specificationArgumentCaptor.capture(), pageableArgumentCaptor.capture());

        Pageable pageable = pageableArgumentCaptor.getValue();
        assertEquals(page, pageable.getPageNumber());
        assertEquals(size, pageable.getPageSize());

        Sort.Order order = pageable.getSort().getOrderFor("price");
        assertNotNull(order);
        assertTrue(order.isAscending());
    }

    @Test
    void givenEmptyFiltersWhenSearchProductThenReturnPageOfProductsAndCallRepository() {
        //given
        Category category = Category.ELECTRONICS;
        Subcategory subcategory = Subcategory.LAPTOPS;
        Map<String, String> filters = Map.of();

        when(productRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(productPage);

        //when
        Page<Product> result = searchService.search(category, subcategory, filters);

        //then
        assertSame(productPage, result);

        @SuppressWarnings("unchecked")
        ArgumentCaptor<Specification<Product>> specificationArgumentCaptor = ArgumentCaptor.forClass(Specification.class);
        ArgumentCaptor<Pageable> pageableArgumentCaptor = ArgumentCaptor.forClass(Pageable.class);

        verify(productRepository, times(1))
                .findAll(specificationArgumentCaptor.capture(), pageableArgumentCaptor.capture());

        Pageable pageable = pageableArgumentCaptor.getValue();
        assertEquals(0, pageable.getPageNumber());
        assertEquals(5, pageable.getPageSize());

        Sort.Order order = pageable.getSort().getOrderFor("id");
        assertNotNull(order);
        assertTrue(order.isAscending());
    }

    @Test
    void givenMapFiltersWhenSearchProductThenReturnPageOfProductsAndCallRepository() {
        //given
        Category category = Category.ELECTRONICS;
        Subcategory subcategory = Subcategory.LAPTOPS;
        Map<String, String> filters = new HashMap<>();
        filters.put("minPrice", "5");
        filters.put("maxPrice", "50");
        filters.put("tags", "tag1,tag2");
        filters.put("sortBy", "name");
        filters.put("page", "2");
        filters.put("size", "20");

        when(productRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(productPage);

        //when
        Page<Product> result = searchService.search(category, subcategory, filters);

        //then
        assertSame(productPage, result);

        @SuppressWarnings("unchecked")
        ArgumentCaptor<Specification<Product>> specificationArgumentCaptor = ArgumentCaptor.forClass(Specification.class);
        ArgumentCaptor<Pageable> pageableArgumentCaptor = ArgumentCaptor.forClass(Pageable.class);

        verify(productRepository, times(1))
                .findAll(specificationArgumentCaptor.capture(), pageableArgumentCaptor.capture());

        Pageable pageable = pageableArgumentCaptor.getValue();
        assertEquals(2, pageable.getPageNumber());
        assertEquals(20, pageable.getPageSize());

        Sort.Order order = pageable.getSort().getOrderFor("name");
        assertNotNull(order);
        assertTrue(order.isAscending());
    }
}
