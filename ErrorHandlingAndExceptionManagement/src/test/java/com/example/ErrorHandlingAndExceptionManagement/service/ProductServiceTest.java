package com.example.ErrorHandlingAndExceptionManagement.service;

import com.example.ErrorHandlingAndExceptionManagement.entity.Product;
import com.example.ErrorHandlingAndExceptionManagement.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.nio.file.AccessDeniedException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
class ProductServiceTest {
    private final ProductRepository productRepository = mock(ProductRepository.class);
    private final ProductService productService = new ProductService(productRepository);
    private Product validProduct = Product.builder()
            .productName("Product Name")
            .productDescription("Product Description")
            .productPrice(new BigDecimal("10.00"))
            .productQuantity(2)
            .build();

    @Test
    void givenValidProductWhenCreateProductThenReturnProductAndCallRepository() {
        // given
        when(productRepository.existsByProductName(validProduct.getProductName())).thenReturn(false);

        // when
        Product result = productService.create(validProduct);

        // then
        verify(productRepository, times(1)).save(validProduct);
    }

    @Test
    void givenInvalidProductWhenCreateProductThenReturnProductAndCallRepository() {
        // given
        when(productRepository.existsByProductName(validProduct.getProductName())).thenReturn(true);

        // when/then
        assertThrows(DataIntegrityViolationException.class, () -> productService.create(validProduct));
    }

    @Test
    void givenValidIdWhenGetProductByIdThenReturnProductAndCallRepository() {
        // given
        Long id = 42L;
        Product p = new Product();
        p.setId(id);
        when(productRepository.findById(id)).thenReturn(Optional.of(p));

        // when
        Product result = productService.getProductById(id);

        // then
        assertSame(p, result);
        verify(productRepository).findById(id);
    }

    @Test
    void givenInvalidIdWhenGetProductByIdThenCallRepositoryAndThrowException() {
        // given
        Long id = 99L;
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        // when / then
        assertThrows(EntityNotFoundException.class,
                () -> productService.getProductById(id));
        verify(productRepository).findById(id);
    }

    @Test
    void givenValidPasswordWhenDeleteProductThenNotThrowAndCallRepository() {
        // given
        Long id = 7L;
        String pwd = "P@ssword";

        // when
        assertDoesNotThrow(() -> productService.deleteById(id, pwd));

        // then
        verify(productRepository).deleteById(id);
    }

    @Test
    void givenInvalidPasswordWhenDeleteProductThenThrowExceptionAndNotCallRepository() {
        // given
        Long id = 7L;
        String bad = "wrong";

        // when / then
        assertThrows(AccessDeniedException.class,
                () -> productService.deleteById(id, bad));

        // and repository not called
        verify(productRepository, never()).deleteById(any());
    }
}
