package com.solactive.tick.statistics.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class StatPrint {
    private BigDecimal avg;
    private BigDecimal max;
    private BigDecimal min;
    private int count;
}
