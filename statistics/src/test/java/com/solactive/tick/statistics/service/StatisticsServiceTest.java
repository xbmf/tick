package com.solactive.tick.statistics.service;

import com.solactive.tick.statistics.domain.MessageEvent;
import com.solactive.tick.statistics.domain.StatPrint;
import com.solactive.tick.statistics.domain.TickMessage;
import com.solactive.tick.statistics.domain.TimeSerriedStat;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Random;

import static com.spotify.hamcrest.pojo.IsPojo.pojo;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class StatisticsServiceTest {

    private TickMessage createRandomInput() {
        return TickMessage.builder()
                .instrument(RandomStringUtils.randomAlphabetic(2))
                .timestamp(Instant.now().minus(new Random().nextInt(60) + 1, SECONDS).toEpochMilli())
                .price(BigDecimal.valueOf(RandomUtils.nextLong(1, 300)))
                .build();
    }

    @Test
    public void testOnInputReceived() {
        var service = new StatisticsService();
        var tickInput = createRandomInput();
        service.onTickInputReceived(new MessageEvent<>(tickInput));

        assertThat(service.getStats().get(tickInput.getInstrument()),
                is(pojo(TimeSerriedStat.class)));

        var stat = service.getStats().get(tickInput.getInstrument());
        assertThat(stat.get(tickInput.getTimestamp()), is(tickInput.getPrice()));
    }

    @Test
    public void testGenerateIndexStats() {
        var service = new StatisticsService();
        var tickInput = createRandomInput();
        service.onTickInputReceived(new MessageEvent<>(tickInput));

        var statsMessage = service.generateIndexStats(Instant.now().minus(60, SECONDS).toEpochMilli());

        assertThat(statsMessage.getAll(), is(pojo(StatPrint.class)
                .withProperty("count", is(1))
                .withProperty("avg", is(tickInput.getPrice()))
                .withProperty("max", is(tickInput.getPrice()))
                .withProperty("min", is(tickInput.getPrice()))));

    }
}
