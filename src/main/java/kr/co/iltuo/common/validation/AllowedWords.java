package kr.co.iltuo.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AllowedWordsValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface AllowedWords {
    String message() default "허용되지 않은 단어입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String[] value();
}
