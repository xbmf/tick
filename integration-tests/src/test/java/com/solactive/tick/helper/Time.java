package com.solactive.tick.helper;

import java.time.Instant;
import java.util.Random;

public class Time {

    public static long randomInPast(int seconds) {
        return Instant.now().minusMillis(randomMilliseconds(seconds)).toEpochMilli();
    }

    private static int randomMilliseconds(int seconds) {
        return new Random().nextInt(seconds * 1000 + 10);
    }
}
