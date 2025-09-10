package com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "product_tag",
        uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "tag_id"})
)
@Getter
public class ProductTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_tag_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Column(name = "added_date")
    private LocalDateTime addedDate;

    @Column(name = "added_by")
    private String addedBy;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProductTag that = (ProductTag) o;
        return Objects.equals(product, that.product) && Objects.equals(tag, that.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, tag);
    }

    ProductTag setAddedBy(String addedBy) {
        this.addedBy = addedBy;
        return this;
    }

    ProductTag setAddedDate(LocalDateTime addedDate) {
        this.addedDate = addedDate;
        return this;
    }

    ProductTag setProduct(Product product) {
        this.product = product;
        return this;
    }

    ProductTag setTag(Tag tag) {
        this.tag = tag;
        return this;
    }
}
