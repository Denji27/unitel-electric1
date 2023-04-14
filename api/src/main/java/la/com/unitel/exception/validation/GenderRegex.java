package la.com.unitel.exception.validation;

import la.com.unitel.UtilImp;
import la.com.unitel.constant.Constants;
import la.com.unitel.entity.constant.Gender;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * @author : Tungct
 * @since : 3/14/2023, Tue
 **/
@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Constraint(validatedBy = {GenderValidator.class})
@Retention(RetentionPolicy.RUNTIME)
public @interface GenderRegex {
    String message() default "The gender is invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

class GenderValidator implements ConstraintValidator<GenderRegex, String> {
    GenderValidator() {
    }

    public void initialize(GenderRegex customConstarint) {
    }

    public boolean isValid(String value, ConstraintValidatorContext cxt) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        } else {
            return Gender.isValid(value.trim());
        }
    }
}

