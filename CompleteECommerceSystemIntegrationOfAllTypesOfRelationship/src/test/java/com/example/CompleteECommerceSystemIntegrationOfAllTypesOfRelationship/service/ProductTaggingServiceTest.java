package com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.service;

import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.EntityInitialization;
import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.entity.Product;
import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.entity.ProductTag;
import com.example.CompleteECommerceSystemIntegrationOfAllTypesOfRelationship.entity.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({ProductTaggingService.class, EntityInitialization.class})
class ProductTaggingServiceTest {
    @Autowired
    private ProductTaggingService service;
    @Autowired
    private TestEntityManager em;
    @Autowired
    private EntityInitialization entityInitialization;
    private EntityInitialization.SeededData seededData;

    @BeforeEach
    void setUp() {
        seededData = entityInitialization.seedFull();
    }

    @Test
    void givenValidProductSkuAndTagNameWhenAddTagToProductThenAddNewTag() {
        //given
        Product testProduct = seededData.products().getFirst();
        String productSku = testProduct.getSku();
        String addedBy = "bartosz";

        Tag testTag = seededData.tags().getLast();
        String tagName = testTag.getName();

        //when
        service.addTagToProduct(productSku, tagName, addedBy);
        em.clear();

        //then
        Product resultProduct = em.find(Product.class, testProduct.getId());
        assertTrue(resultProduct.getTags().contains(testTag));

        Tag resultTag = em.find(Tag.class, testTag.getId());
        assertTrue(resultTag.getProductTags()
                .stream()
                .map(ProductTag::getProduct)
                .anyMatch(product -> product.getId().equals(testProduct.getId())));
    }

    @Test
    void givenAlreadyAddedTagWhenAddTagToProductThenThrowException() {
        //given
        Product testProduct = seededData.products().getFirst();
        String productSku = testProduct.getSku();
        String addedBy = "bartosz";

        Tag testTag = seededData.tags().getFirst();
        String tagName = testTag.getName();

        //when / then
        Exception exception = assertThrows(RuntimeException.class,
                () -> service.addTagToProduct(productSku, tagName, addedBy));

        assertEquals("Product tag already exists", exception.getMessage());
    }

    @Test
    void givenTagsNamesWhenFindProductsByTagThenReturnProducts() {
        //given
        Set<String> tagsNames = Set.of("New");
        Product expectedProduct = seededData.products().getFirst();
        Product expectedProduct2 = seededData.products().getLast();

        //when
        List<Product> result = service.findProductsByTag(tagsNames);

        //then
        assertThat(result)
                .containsExactlyInAnyOrder(expectedProduct, expectedProduct2);
    }
}
