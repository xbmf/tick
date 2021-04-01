package com.solactive.tick.api.domain;

import com.solactive.tick.api.validation.ValidTimestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TickInput implements Serializable {
    @NotEmpty
    @NotNull
    private String instrument;

    @NotNull
    @Digits(integer = 10, fraction = 2)
    @Positive(message = "The value must be positive")
    private BigDecimal price;

    @ValidTimestamp
    private long timestamp;
}
