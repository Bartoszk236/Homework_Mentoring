package com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "product")
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "sku", unique = true)
    private String sku;

    @Column(name = "price", precision = 6, scale = 2)
    private BigDecimal price;

    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ProductTag> productTags = new HashSet<>();

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItem = new ArrayList<>();

    @Version
    private Long version;

    public Set<Tag> getTags() {
        return productTags.stream()
                .map(ProductTag::getTag)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(sku, product.sku);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(sku);
    }

    public void addTagToProduct(Tag tag, String addedBy) {
        ProductTag productTag = new ProductTag()
                .setAddedBy(addedBy)
                .setAddedDate(LocalDateTime.now())
                .setTag(tag)
                .setProduct(this);

        this.productTags.add(productTag);
        tag.addProductTag(productTag);
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public Product setSku(String sku) {
        this.sku = sku;
        return this;
    }

    public Product setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Product setStockQuantity(Integer stockQuantity) {
        if (stockQuantity < 0) throw new IllegalArgumentException("Stock quantity cannot be negative");
        this.stockQuantity = stockQuantity;
        return this;
    }

    public Product setCategory(Category category) {
        this.category = category;
        return this;
    }

    void addOrderItem(OrderItem orderItem) {
        this.orderItem.add(orderItem);
    }
}
