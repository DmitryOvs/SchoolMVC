package org.chukotka.school.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = CurrentYearValidator.class)
@Documented
public @interface CurrentYear {
    String message() default "Возраст не может быть меньше 6 лет";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
