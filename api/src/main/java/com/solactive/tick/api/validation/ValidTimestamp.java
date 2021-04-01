package com.solactive.tick.api.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidTimestampImpl.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTimestamp {
    String message() default "Invalid timestamp";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
