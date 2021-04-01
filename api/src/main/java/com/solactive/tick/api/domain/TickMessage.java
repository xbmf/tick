package com.solactive.tick.api.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TickMessage {
    private String instrument;

    private BigDecimal price;

    private long timestamp;

    public static TickMessage fromTickInput(TickInput input) {
        return TickMessage.builder()
                .instrument(input.getInstrument().toUpperCase())
                .price(new BigDecimal(input.getPrice().toString()))
                .timestamp(input.getTimestamp())
                .build();
    }
}
