package la.com.unitel.exception.validation;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * @author : Tungct
 * @since : 3/14/2023, Tue
 **/
@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Constraint(validatedBy = {DateValidator.class})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateRegex {
    String message() default "Invalid date format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

class DateValidator implements ConstraintValidator<DateRegex, String> {
    DateValidator() {
    }

    public void initialize(DateRegex customConstarint) {
    }

    public boolean isValid(String value, ConstraintValidatorContext cxt) {
        if (value == null || value.trim().isEmpty()) {
            return true;
        } else {
            try {
                LocalDate.parse(value.trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException e) {
                return false;
            }
            return true;
        }
    }
}

