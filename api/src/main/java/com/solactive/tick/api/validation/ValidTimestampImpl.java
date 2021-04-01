package com.solactive.tick.api.validation;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Instant;

import static java.time.temporal.ChronoUnit.SECONDS;

@Slf4j
public class ValidTimestampImpl implements ConstraintValidator<ValidTimestamp, Long> {

    /**
     * Check given timestamp is not coming from the future and less than 60 secs
     *
     * @param timestamp
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(Long timestamp, ConstraintValidatorContext constraintValidatorContext) {
        var input = Instant.ofEpochMilli(timestamp);
        return input.compareTo(Instant.now()) <= 0 &&
                input.compareTo(Instant.now().minus(60, SECONDS)) >= 0;
    }
}
