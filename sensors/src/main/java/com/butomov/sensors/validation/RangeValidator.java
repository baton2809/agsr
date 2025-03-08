package com.butomov.sensors.validation;

import com.butomov.sensors.model.SensorRange;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RangeValidator implements ConstraintValidator<ValidRange, SensorRange> {

    @Override
    public boolean isValid(SensorRange range, ConstraintValidatorContext context) {
        if (range == null || range.getRangeFrom() == null || range.getRangeTo() == null) {
            return false;
        }
        if (range.getRangeFrom() < 0 || range.getRangeTo() > 100) {
            return false;
        }
        return range.getRangeFrom() < range.getRangeTo();
    }
}
