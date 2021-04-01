package com.solactive.tick.statistics.broker.producer;

import com.solactive.tick.statistics.domain.IndexStatsMessage;

public interface Producer {
    void publishStats(IndexStatsMessage message);
}
