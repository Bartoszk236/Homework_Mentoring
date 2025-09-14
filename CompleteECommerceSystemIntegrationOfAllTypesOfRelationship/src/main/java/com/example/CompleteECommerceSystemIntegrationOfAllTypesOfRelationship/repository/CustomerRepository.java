package com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.repository;

import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.entity.Category;
import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("""
            select c from Customer c
            where c not in (
                select distinct o.customer from Order o
                where o.orderDate > :threeMonthsAgo
            )
            """)
    List<Customer> findInactiveCustomers(@Param("threeMonthsAgo") LocalDateTime date);

    @Query("""
            select sum(o.totalAmount)
            from Order o
            where o.customer.id = :customerId
            """)
    Optional<BigDecimal> findTotalValueOrdersByCustomerId(@Param("customerId") Long customerId);

    @Query("""
            select count(o)
            from Customer c
            join c.orders o
            where c.id = :customerId
            """)
    Integer findCountOfOrdersByCustomerId(@Param("customerId") Long customerId);

    @Query("""
            select cat
            from OrderItem oi
            join oi.order o
            join oi.product p
            join p.category cat
            where o.customer.id = :customerId
            group by cat.name
            order by sum(oi.quantity) desc, cat.name asc
            limit 1
            """)
    Category findFavoriteCategoryByCustomerId(@Param("customerId") Long customerId);

    @Query("""
            select o.orderDate
            from Order o
            where o.customer.id = :customerId
            order by o.orderDate desc
            limit 1
            """)
    LocalDateTime findLastOrderDateByCustomerId(@Param("customerId") Long customerId);
}
