package com.solactive.tick.bot;

import com.solactive.tick.helper.Instrument;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * It creates WRITE bots that posting tick payload in random time between (0-$slidingTimeInterval) to the API instance
 * Also it creates a READ bot that reads the current stats for all instruments in 1 second interval
 *
 * When all WRITE bots completed their operation, countdownlatch will finalize the process.
 */
public class BotManager {
    private int slidingTimeInterval;
    private int numberOfRequestPerInstrument;
    private int instrumentSize;

    private ScheduledThreadPoolExecutor threadPoolExecutor;

    private CountDownLatch countDownLatch;

    public BotManager(int slidingTimeInterval, int numberOfRequestPerInstrument,
                      int instrumentSize) {
        this.slidingTimeInterval = slidingTimeInterval;
        this.numberOfRequestPerInstrument = numberOfRequestPerInstrument;
        this.instrumentSize = instrumentSize;

        threadPoolExecutor = new ScheduledThreadPoolExecutor(8);
        countDownLatch = new CountDownLatch(numberOfRequestPerInstrument * instrumentSize);
    }

    public void run() throws InterruptedException {
        var instruments = Instrument.random(instrumentSize);

        for (String instrument : instruments) {
            for (int i = 0; i < numberOfRequestPerInstrument; i++) {
                var tickBot = new TickBot(countDownLatch, instrument, slidingTimeInterval);
                threadPoolExecutor.schedule(tickBot, new Random().nextInt(slidingTimeInterval), TimeUnit.SECONDS);
            }
        }
        var readBot = new ReadBot(instruments);
        threadPoolExecutor.scheduleAtFixedRate(readBot, 1, 1, TimeUnit.SECONDS);
        countDownLatch.await();
    }
}
