package la.com.unitel.exception.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author : Tungct
 * @since : 3/14/2023, Tue
 **/
@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Constraint(validatedBy = {MonthCodeValidator.class})
@Retention(RetentionPolicy.RUNTIME)
public @interface MonthCodeRegex {
    String message() default "The month code is invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

class MonthCodeValidator implements ConstraintValidator<MonthCodeRegex, String> {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    MonthCodeValidator() {
    }

    public void initialize(MonthCodeRegex customConstarint) {
    }

    public boolean isValid(String value, ConstraintValidatorContext cxt) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        } else {
            return verifyMonthCode(value.trim());
        }
    }

    private Boolean verifyMonthCode(String monthCode) {
        logger.info("Verify month code for {}", monthCode);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
        if (monthCode != null) {
            try {
                Date date = simpleDateFormat.parse(monthCode.trim());
                logger.info("Parse date: {}", date);
                return simpleDateFormat.format(date).equals(monthCode.trim()) && (LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM")).equals(monthCode.trim()));
            } catch (ParseException e) {
                logger.error("Parse date error due to: ", e);
            }
        }
        return false;
    }
}

