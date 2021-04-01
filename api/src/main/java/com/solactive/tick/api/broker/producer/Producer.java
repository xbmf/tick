package com.solactive.tick.api.broker.producer;

import com.solactive.tick.api.domain.TickInput;

public interface Producer {
    void publishTick(TickInput tickInput);
}
