package com.solactive.tick.api.service;

import com.solactive.tick.api.domain.IndexStatsMessage;
import com.solactive.tick.api.domain.InstrumentStat;
import com.solactive.tick.api.domain.MessageEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.SECONDS;

@Service
public class StatisticsService {

    private InstrumentStat all;

    private Instant updatedAt;

    private Map<String, InstrumentStat> instruments = new HashMap<>();

    @Value("${api.constraints.sliding-time-interval}")
    private int slidingTimeInterval;

    @EventListener
    public void onNewStatsAvailable(MessageEvent<IndexStatsMessage> messageEvent) {

        var message = messageEvent.getMessage();

        updatedAt = Instant.ofEpochMilli(message.getUpdatedAt());
        all = InstrumentStat.from(message.getAll());

        var newInstruments = new HashMap<String, InstrumentStat>();
        message.getInstruments().forEach((instrument, stat) -> newInstruments.put(instrument, InstrumentStat.from(stat)));
        instruments = newInstruments;
    }

    public Optional<InstrumentStat> getForAll() {
        if (updatedAt == null || !isDataUpToDate()) {
            return Optional.empty();
        }
        return Optional.of(all);
    }

    public Optional<InstrumentStat> getForInstrument(String instrument) {
        if (updatedAt == null || !isDataUpToDate()) {
            return Optional.empty();
        }
        return Optional.ofNullable(instruments.get(instrument));
    }

    private boolean isDataUpToDate() {
        return updatedAt.compareTo(Instant.now().minus(slidingTimeInterval, SECONDS)) >= 0;
    }
}
