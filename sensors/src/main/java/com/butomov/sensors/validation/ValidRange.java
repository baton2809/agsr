package com.butomov.sensors.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RangeValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRange {
    String message() default "RangeFrom should be less than RangeTo and each should be in range [0,100]";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
