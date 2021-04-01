package com.solactive.tick.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatPrint {
    private BigDecimal avg;
    private BigDecimal max;
    private BigDecimal min;
    private int count;
}
