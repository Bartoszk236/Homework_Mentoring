package com.example.QueryParametersAndPathVariables.contorller;

import com.example.QueryParametersAndPathVariables.service.SearchService;
import com.example.QueryParametersAndPathVariables.entity.Product;
import com.example.QueryParametersAndPathVariables.model.Category;
import com.example.QueryParametersAndPathVariables.model.SortBy;
import com.example.QueryParametersAndPathVariables.model.Subcategory;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ProductSearchController {
    private final SearchService searchService;

    @GetMapping("/api/search/products")
    public ResponseEntity<Page<Product>> searchProducts(
            @RequestParam Category category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) List<String> tags,
            @RequestParam(required = false) SortBy sortBy,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "5") @Min(1) int size
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(searchService.search(category, minPrice, maxPrice, tags, sortBy, page, size));
    }

    @GetMapping("/api/products/{category}/{subcategory}")
    public ResponseEntity<Page<Product>> searchProducts(
            @PathVariable Category category,
            @PathVariable Subcategory subcategory,
            @RequestParam Map<String, String> filters
            ) {
        return ResponseEntity.status(HttpStatus.OK).body(searchService.search(category, subcategory, filters));
    }
}
