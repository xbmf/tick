package com.solactive.tick.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndexStatsMessage {
    private StatPrint all;
    private Map<String, StatPrint> instruments;
    private Long updatedAt;
}
