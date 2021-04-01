package com.solactive.tick.statistics.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class IndexStatsMessage {
    private StatPrint all;

    private Map<String, StatPrint> instruments;

    private long updatedAt;
}
