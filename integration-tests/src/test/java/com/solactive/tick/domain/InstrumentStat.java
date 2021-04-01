package com.solactive.tick.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstrumentStat {
    private BigDecimal avg;
    private BigDecimal max;
    private BigDecimal min;
    private int count;
}
