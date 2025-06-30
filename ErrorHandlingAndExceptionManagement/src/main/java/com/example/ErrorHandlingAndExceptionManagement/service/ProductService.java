package com.example.ErrorHandlingAndExceptionManagement.service;

import com.example.ErrorHandlingAndExceptionManagement.entity.Product;
import com.example.ErrorHandlingAndExceptionManagement.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product create(Product product) {
        if (productRepository.existsByProductName(product.getProductName()))
            throw new DataIntegrityViolationException(null);
        return productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public void deleteById(Long id, String password) throws AccessDeniedException {
        if (!password.equals("P@ssword")) throw new AccessDeniedException(null);
        productRepository.deleteById(id);
    }
}
