package com.example.JsonDeserializationWithCustomLogic.validator;

import com.example.JsonDeserializationWithCustomLogic.adnotation.TotalAmount;
import com.example.JsonDeserializationWithCustomLogic.dto.OrderItem;
import com.example.JsonDeserializationWithCustomLogic.dto.OrderRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Slf4j
public class TotalAmountValidator implements ConstraintValidator<TotalAmount, OrderRequest> {
    @Override
    public boolean isValid(OrderRequest request, ConstraintValidatorContext context) {
        if (request.getItems() == null || request.getItems().isEmpty()) {
            return false;
        }
        if (request.getDiscount() == null || request.getTotalAmount() == null) {
            return false;
        }

        List<OrderItem> items = request.getItems();
        BigDecimal discount = request.getDiscount();
        BigDecimal totalAmount = calculateTotalAmount(items);
        BigDecimal discountAmount = calculateDiscountAmount(totalAmount, discount);
        BigDecimal finalTotalAmount = totalAmount.subtract(discountAmount);
        BigDecimal exceptedAmount = request.getTotalAmount();

        if (finalTotalAmount.compareTo(exceptedAmount) != 0) {
            log.error("Total amount is incorrect. Excepted: {} Actual: {}", exceptedAmount, finalTotalAmount);
            return false;
        }
        return true;
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> items) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (OrderItem item : items) {
            BigDecimal price = item.unitPrice();
            Integer quantity = item.quantity();
            totalAmount = totalAmount
                    .add(price.multiply(BigDecimal.valueOf(quantity)));
        }
        return totalAmount;
    }

    private BigDecimal calculateDiscountAmount(BigDecimal totalAmount, BigDecimal discount) {
        BigDecimal discountAmount = BigDecimal.ZERO;

        if (discount.compareTo(BigDecimal.ZERO) == 0) return discountAmount;

        if (discount.compareTo(BigDecimal.ONE) > 0) {
            return discount;
        } else {
            return totalAmount
                    .multiply(discount)
                    .setScale(2, RoundingMode.HALF_UP);
        }
    }
}
