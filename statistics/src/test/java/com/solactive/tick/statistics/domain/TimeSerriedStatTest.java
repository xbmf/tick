package com.solactive.tick.statistics.domain;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static java.time.temporal.ChronoUnit.MINUTES;
import static java.time.temporal.ChronoUnit.SECONDS;

public class TimeSerriedStatTest {

    @Test
    public void testGetAll() {
        var data = new TimeSerriedStat();
        long oneSecondAgo = Instant.now().minus(1, SECONDS).toEpochMilli();
        long twoSecondAgo = Instant.now().minus(2, SECONDS).toEpochMilli();

        long twoMinutesAgo = Instant.now().minus(2, MINUTES).toEpochMilli();

        data.add(oneSecondAgo, BigDecimal.valueOf(1));
        data.add(twoSecondAgo, BigDecimal.valueOf(2));
        data.add(twoMinutesAgo, BigDecimal.valueOf(3));

        long oneMinuteAgo = Instant.now().minus(1, MINUTES).toEpochMilli();

        List<BigDecimal> valuesInOneMinute = data.getAllAfter(oneMinuteAgo);

        Assert.assertEquals(valuesInOneMinute.size(), 2);
        Assert.assertEquals(valuesInOneMinute.get(0), BigDecimal.valueOf(1));
        Assert.assertEquals(valuesInOneMinute.get(1), BigDecimal.valueOf(2));
        Assert.assertEquals(data.size(), 2);
        Assert.assertEquals(data.get(oneSecondAgo), BigDecimal.valueOf(1));
        Assert.assertEquals(data.get(twoSecondAgo), BigDecimal.valueOf(2));
        Assert.assertEquals(data.get(twoMinutesAgo), null);
    }
}
