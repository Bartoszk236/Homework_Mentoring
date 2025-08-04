package com.example.JsonDeserializationWithCustomLogic.validator;

import com.example.JsonDeserializationWithCustomLogic.dto.OrderRequest;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TotalAmountValidatorTest {
    private final TotalAmountValidator totalAmountValidator = new TotalAmountValidator();
    private final OrderRequestBuilder orderRequestBuilder = new OrderRequestBuilder();

    @Test
    void givenNullItemsListWhenValidateThenFalse() {
        //given
        OrderRequest givenRequest = orderRequestBuilder.createOrderWithItemsNull();
        ConstraintValidatorContext context = Mockito.mock(ConstraintValidatorContext.class);

        //when
        boolean result = totalAmountValidator.isValid(givenRequest, context);

        //then
        Assertions.assertFalse(result);
    }

    @Test
    void givenEmptyItemsListWhenValidateThenFalse() {
        //given
        OrderRequest givenRequest = orderRequestBuilder.createOrderWithItemsEmptyList();
        ConstraintValidatorContext context = Mockito.mock(ConstraintValidatorContext.class);

        //when
        boolean result = totalAmountValidator.isValid(givenRequest, context);

        //then
        Assertions.assertFalse(result);
    }

    @Test
    void givenDiscountNullWhenValidateThenFalse() {
        //given
        OrderRequest givenRequest = orderRequestBuilder.createOrderWithItemsNull();
        ConstraintValidatorContext context = Mockito.mock(ConstraintValidatorContext.class);

        //when
        boolean result = totalAmountValidator.isValid(givenRequest, context);

        //then
        Assertions.assertFalse(result);
    }

    @Test
    void givenTotalAmountNullWhenValidateThenFalse() {
        //given
        OrderRequest givenRequest = orderRequestBuilder.createOrderWithTotalAmountNull();
        ConstraintValidatorContext context = Mockito.mock(ConstraintValidatorContext.class);

        //when
        boolean result = totalAmountValidator.isValid(givenRequest, context);

        //then
        Assertions.assertFalse(result);
    }

    @Test
    void giveValidDataWithDiscountAmountWhenValidateThenTrue() {
        //given
        OrderRequest givenRequest = orderRequestBuilder.createOrderRequestWithValidDataAndDiscountAmount();
        ConstraintValidatorContext context = Mockito.mock(ConstraintValidatorContext.class);

        //when
        boolean result = totalAmountValidator.isValid(givenRequest, context);

        //then
        Assertions.assertTrue(result);
    }

    @Test
    void giveValidDataWithDiscountPercentWhenValidateThenTrue() {
        //given
        OrderRequest givenRequest = orderRequestBuilder.createOrderRequestWithValidDataAndDiscountPercent();
        ConstraintValidatorContext context = Mockito.mock(ConstraintValidatorContext.class);

        //when
        boolean result = totalAmountValidator.isValid(givenRequest, context);

        //then
        Assertions.assertTrue(result);
    }

    @Test
    void giveInvalidDataWithDiscountNullWhenValidateThenFalse() {
        //given
        OrderRequest givenRequest = orderRequestBuilder.createOrderWithDiscountNull();
        ConstraintValidatorContext context = Mockito.mock(ConstraintValidatorContext.class);

        //when
        boolean result = totalAmountValidator.isValid(givenRequest, context);

        //then
        Assertions.assertFalse(result);
    }

    @Test
    void givenInvalidMatchWithActualAndExceptedAmountWhenValidateThenFalse() {
        //given
        OrderRequest givenRequest = orderRequestBuilder.createOrderWithMissMatchActualAndExceptedTotalAmount();
        ConstraintValidatorContext context = Mockito.mock(ConstraintValidatorContext.class);

        //when
        boolean result = totalAmountValidator.isValid(givenRequest, context);

        //then
        Assertions.assertFalse(result);
    }
}
