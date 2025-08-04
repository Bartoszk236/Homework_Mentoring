package com.example.JsonDeserializationWithCustomLogic.adnotation;

import com.example.JsonDeserializationWithCustomLogic.validator.TotalAmountValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TotalAmountValidator.class)
public @interface TotalAmount {
    String message() default "totalAmount does not match sum of items";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
