package com.example.contoller.with.advanced.validation.validation;

import com.example.contoller.with.advanced.validation.annotation.CustomISBN;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class ISBNValidator implements ConstraintValidator<CustomISBN, String> {

    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext context) {
        if (isbn == null) return false;
        return isbn.replaceAll("-", "").replaceAll(" ", "").length() == 13;
    }
}
