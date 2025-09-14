package com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.repository;

import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.entity.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTagRepository extends JpaRepository<ProductTag, Long> {
    boolean existsByTagIdAndProductId(Long tagId, Long productId);
}
