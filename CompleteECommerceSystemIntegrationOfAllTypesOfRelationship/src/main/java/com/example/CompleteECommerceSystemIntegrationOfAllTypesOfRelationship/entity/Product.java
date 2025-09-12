package com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @Setter
    @Column(name = "name")
    private String name;

    @Setter
    @Column(name = "sku", unique = true)
    private String sku;

    @Setter
    @Column(name = "price", precision = 6, scale = 2)
    private BigDecimal price;

    @Setter
    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @Setter
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
        ProductTag productTag = new ProductTag();
        productTag.setAddedBy(addedBy);
        productTag.setAddedDate(LocalDateTime.now());
        productTag.setTag(tag);
        productTag.setProduct(this);

        this.productTags.add(productTag);
        tag.addProductTag(productTag);
    }

    void addOrderItem(OrderItem orderItem) {
        this.orderItem.add(orderItem);
    }
}
