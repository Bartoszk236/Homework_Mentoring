package com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.repository;

import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findBySku(String sku);

    @Query("""
            select p from Product p
            join p.productTags pt
            join pt.tag t
            where t.name in :tagNames
            group by p
            having count(distinct t.name) >= :tagsCount
            """)
    List<Product> findByTags(@Param("tagNames") Set<String> tagNames,
                                    @Param("tagsCount") long tagsCount);

    @Query("""
            select p, sum(oi.quantity) as totalSold
            from Product p
            join p.orderItem oi
            join oi.order o
            where o.status = 'COMPLETED'
            group by p
            order by totalSold desc
            """)
    List<Object[]> findTopSellingProducts(Pageable pageable);

    @Query("""
            select p
            from Product p
            where p.stockQuantity <= :lessOrEqualsTo
            """)
    List<Product> findProductWithLowStocks(@Param("lessOrEqualsTo") Integer lessOrEqualsTo);

    @Query("""
            select oi.product
            from OrderItem oi
            group by oi.product
            order by sum(oi.quantity) desc
            limit 1
            """)
    Product findTheBestSellingProduct();
}
