package com.solactive.tick.statistics.broker.consumer;

public interface Consumer {
    void receiveTick(String message);
}
