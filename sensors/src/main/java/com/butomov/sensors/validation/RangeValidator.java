package com.butomov.sensors.validation;

import com.butomov.sensors.dto.response.RangeDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

public class RangeValidator implements ConstraintValidator<ValidRange, RangeDto> {

    @Override
    public boolean isValid(RangeDto range, ConstraintValidatorContext context) {
        if (range == null) {
            return false;
        }

        int rangeFrom = range.getRangeFrom();
        int rangeTo = range.getRangeTo();

        List<String> errors = new ArrayList<>();
        if (rangeFrom >= rangeTo) {
            errors.add("'rangeFrom' must be less than 'rangeTo'");
        }
        if (rangeFrom < 0 || rangeFrom > 100) {
            errors.add("'rangeFrom' should be in range [0,100]");
        }
        if (rangeTo > 100 || rangeTo < 0) {
            errors.add("'rangeTo' should be in range [0,100]");
        }

        if (!errors.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            "Invalid range: " + String.join(" and ", errors))
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
