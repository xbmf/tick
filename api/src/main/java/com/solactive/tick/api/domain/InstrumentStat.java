package com.solactive.tick.api.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class InstrumentStat {
    private BigDecimal avg;
    private BigDecimal max;
    private BigDecimal min;
    private int count;

    public static InstrumentStat from(StatPrint statPrint) {
        return InstrumentStat.builder()
                .avg(new BigDecimal(statPrint.getAvg().toString()))
                .max(new BigDecimal(statPrint.getMax().toString()))
                .min(new BigDecimal(statPrint.getMin().toString()))
                .count(statPrint.getCount()).build();

    }
}
