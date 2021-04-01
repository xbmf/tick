package com.solactive.tick.bot;

import com.solactive.tick.client.ApiClient;
import com.solactive.tick.domain.TickInput;
import com.solactive.tick.helper.Price;
import com.solactive.tick.helper.Time;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class TickBot implements Runnable {

    private CountDownLatch latch;

    private String instrument;

    private ApiClient apiClient;

    private int slidingTimeInterval;

    public TickBot(CountDownLatch latch, String instrument, int slidingTimeInterval) {
        this.latch = latch;
        this.instrument = instrument;
        this.apiClient = new ApiClient();
        this.slidingTimeInterval = slidingTimeInterval;
    }

    @Override
    public void run() {
        var tickInput = TickInput.builder()
                .timestamp(Time.randomInPast(slidingTimeInterval))
                .price(Price.random())
                .instrument(instrument).build();
        try {
            var responseCode = apiClient.tick(tickInput);
            if (responseCode != 201) {
                log.warn("tickinput: {} is invalid", tickInput);
            }
        } catch (Exception e) {
            log.error("error on tick bot", e);
        } finally {
            latch.countDown();
        }
    }
}
