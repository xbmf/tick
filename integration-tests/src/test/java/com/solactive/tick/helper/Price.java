package com.solactive.tick.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Price {
    private static final int RANGE = 100;

    public static BigDecimal random() {
        BigDecimal max = new BigDecimal(RANGE);
        BigDecimal randFromDouble = new BigDecimal(Math.random());
        BigDecimal actualRandomDec = randFromDouble.multiply(max);
        actualRandomDec = actualRandomDec
                .setScale(2, RoundingMode.HALF_EVEN);
        return actualRandomDec;
    }
}
