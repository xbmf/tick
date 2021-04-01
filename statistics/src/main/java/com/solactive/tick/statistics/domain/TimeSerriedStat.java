package com.solactive.tick.statistics.domain;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Data
@NoArgsConstructor
public class TimeSerriedStat {
    private TreeMap<Long, BigDecimal> stats = new TreeMap<>(Comparator.reverseOrder());

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public BigDecimal add(Long timestamp, BigDecimal value) {
        var writeLock = lock.writeLock();
        try {
            writeLock.lock();
            return stats.putIfAbsent(timestamp, value);
        } finally {
            writeLock.unlock();
        }
    }

    public BigDecimal get(Long timestamp) {
        var readLock = lock.readLock();
        try {
            readLock.lock();
            return stats.get(timestamp);
        } finally {
            readLock.unlock();
        }
    }

    public List<BigDecimal> getAllAfter(Long timestamp) {
        var values = new ArrayList<BigDecimal>();
        var readLock = lock.readLock();
        var writeLock = lock.writeLock();

        try {
            readLock.lock();
            values.addAll(stats.headMap(timestamp, true).values());
        } finally {
            readLock.unlock();
        }

        try {
            writeLock.lock();
            stats.tailMap(timestamp).clear();
        } finally {
            writeLock.unlock();
        }
        return values;
    }

    public int size() {
        var readLock = lock.readLock();
        try {
            readLock.lock();
            return stats.size();
        } finally {
            readLock.unlock();
        }
    }
}
