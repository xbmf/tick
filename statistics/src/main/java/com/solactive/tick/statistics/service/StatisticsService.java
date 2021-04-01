package com.solactive.tick.statistics.service;

import com.solactive.tick.statistics.domain.*;
import com.solactive.tick.statistics.helper.StatCalculator;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class StatisticsService {

    private Map<String, TimeSerriedStat> stats = new HashMap<>();

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    @EventListener
    public void onTickInputReceived(MessageEvent<TickMessage> tickInputMessage) {
        var writeLock = lock.writeLock();
        var tickInput = tickInputMessage.getData();
        TimeSerriedStat instrumentStat;
        try {
            writeLock.lock();
            instrumentStat = stats.computeIfAbsent(tickInput.getInstrument(), t -> new TimeSerriedStat());
        } finally {
            writeLock.unlock();
        }
        instrumentStat.add(tickInput.getTimestamp(), tickInput.getPrice());
    }

    public Map<String, TimeSerriedStat> getStats() {
        var readLock = lock.readLock();
        try {
            readLock.lock();
            return stats;
        } finally {
            readLock.unlock();
        }
    }

    public IndexStatsMessage generateIndexStats(long timestamp) {
        var readLock = lock.readLock();
        var instrumentStatMap = new HashMap<String, StatPrint>();

        var updatedAt = Instant.now().toEpochMilli();

        try {
            readLock.lock();
            var overallStatCalculator = new StatCalculator();
            stats.forEach((instrument, instrumentStat) -> {
                var statCalculator = new StatCalculator();
                instrumentStat.getAllAfter(timestamp).forEach(statCalculator::add);
                overallStatCalculator.merge(statCalculator);
                if (statCalculator.getCount() > 0) {
                    instrumentStatMap.put(instrument, statCalculator.toStatOutput());
                }
            });
            return new IndexStatsMessage(overallStatCalculator.toStatOutput(), instrumentStatMap, updatedAt);
        } finally {
            readLock.unlock();
        }
    }
}
