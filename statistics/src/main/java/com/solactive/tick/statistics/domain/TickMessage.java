package com.solactive.tick.statistics.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TickMessage implements Serializable {

    private String instrument;

    private BigDecimal price;

    private long timestamp;
}
