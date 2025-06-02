package kr.co.iltuo.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class AllowedWordsValidator implements ConstraintValidator<AllowedWords, String> {

    private List<String> allowedWords;

    @Override
    public void initialize(AllowedWords constraintAnnotation) {
        allowedWords = Arrays.asList(constraintAnnotation.value());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return allowedWords.contains(value);
    }
}
