package la.com.unitel.exception.validation;

import la.com.unitel.Constants;
import la.com.unitel.UtilImp;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.regex.Pattern;

/**
 * @author : Tungct
 * @since : 3/14/2023, Tue
 **/
@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Constraint(validatedBy = {PhoneNumberValidator.class})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumberRegex {
    String message() default "The phone number is not match regex";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

class PhoneNumberValidator implements ConstraintValidator<PhoneNumberRegex, String> {
    PhoneNumberValidator() {
    }

    public void initialize(PhoneNumberRegex customConstarint) {
    }

    public boolean isValid(String value, ConstraintValidatorContext cxt) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        } else {
            String regex = Constants.PHONE_NUMBER_PATTERN;
            return Pattern.matches(regex, new UtilImp().toMsisdn(value.trim()));
        }
    }
}

