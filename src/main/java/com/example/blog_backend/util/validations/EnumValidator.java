package com.example.blog_backend.util.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EnumValidator implements ConstraintValidator<ValidEnum, CharSequence> {

    private List<String> acceptedValues;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        this.acceptedValues = Arrays.stream(constraintAnnotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null || value.toString().isEmpty()) {
            return false; // Null veya boş string geçersiz
        }
        return acceptedValues.contains(value.toString());
    }
}