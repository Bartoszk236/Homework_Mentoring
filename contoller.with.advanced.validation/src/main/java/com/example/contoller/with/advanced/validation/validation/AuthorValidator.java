package com.example.contoller.with.advanced.validation.validation;

import com.example.contoller.with.advanced.validation.annotation.AuthorAnnotation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class AuthorValidator implements ConstraintValidator<AuthorAnnotation, String> {

    @Override
    public boolean isValid(String author, ConstraintValidatorContext context) {
        int length = author.trim().split("\\s+").length;
        return length >= 2;
    }
}
