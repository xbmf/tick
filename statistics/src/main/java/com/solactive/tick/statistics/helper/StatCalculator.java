package com.solactive.tick.statistics.helper;

import com.solactive.tick.statistics.domain.StatPrint;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
public class StatCalculator {
    private int count = 0;

    private BigDecimal total = BigDecimal.ZERO;

    private BigDecimal min = BigDecimal.ZERO;

    private BigDecimal max = BigDecimal.ZERO;


    public void add(BigDecimal value) {
        count += 1;
        total = total.add(value);

        if (value.compareTo(max) > 0) {
            max = value;
        }

        if (min.equals(BigDecimal.ZERO) || value.compareTo(min) < 0) {
            min = value;
        }
    }

    public void merge(StatCalculator calculator) {
        count += calculator.getCount();
        total = total.add(calculator.getTotal());

        if (calculator.getMax().compareTo(max) > 0) {
            max = calculator.getMax();
        }
        if (min.equals(BigDecimal.ZERO) || calculator.getMin().compareTo(min) < 0) {
            min = calculator.getMin();
        }
    }

    public StatPrint toStatOutput() {
        var builder = StatPrint.builder()
                .min(min)
                .max(max)
                .count(count);
        if (total.equals(BigDecimal.ZERO)) {
            builder.avg(BigDecimal.ZERO);
        } else {
            builder.avg(total.divide(BigDecimal.valueOf(count), RoundingMode.HALF_UP));
        }
        return builder.build();
    }

}
