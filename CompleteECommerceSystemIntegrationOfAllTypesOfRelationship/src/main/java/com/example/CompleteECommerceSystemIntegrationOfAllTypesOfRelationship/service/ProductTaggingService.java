package com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.service;

import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.entity.Product;
import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.entity.Tag;
import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.repository.ProductRepository;
import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.repository.ProductTagRepository;
import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductTaggingService {
    private final TagRepository tagRepository;
    private final ProductRepository productRepository;
    private final ProductTagRepository productTagRepository;

    @Transactional
    public void addTagToProduct(String productSku, String tagName) {
        Tag tag = tagRepository.findByName(tagName).orElseThrow(
                () -> new RuntimeException("No tag found with name: " + tagName)
        );

        Product product = productRepository.findBySku(productSku).orElseThrow(
                () -> new RuntimeException("No product found with sku: " + productSku));

        if (productTagRepository.existsByTagIdAndProductId(tag.getId(), product.getId()))
            throw new RuntimeException("Product tag already exists");

        String addedBy = "Bartosz Admin";
        product.addTagToProduct(tag, addedBy);
    }

    public List<Product> findProductsByTag(Set<String> tagNames) {
        long size = tagNames.size();
        return productRepository.findByTags(tagNames, size);
    }
}
