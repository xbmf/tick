package com.solactive.tick;

import com.solactive.tick.bot.BotManager;
import com.solactive.tick.client.ApiClient;
import com.solactive.tick.domain.InstrumentStat;
import com.solactive.tick.domain.TickInput;
import com.solactive.tick.helper.Time;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.spotify.hamcrest.pojo.IsPojo.pojo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@SpringBootTest
@ActiveProfiles("test")
public class ConcurrencyTestIT {
    @Value("${test.sliding-time-interval}")
    int slidingTimeInterval;

    @Value("${test.number-of-requests-per-instrument}")
    int numberOfRequestPerInstrument;

    @Value("${test.instrument-size}")
    int instrumentSize;

    @Test
    public void testConcurrentRequests() throws InterruptedException {
        var botManager = new BotManager(slidingTimeInterval, numberOfRequestPerInstrument, instrumentSize);
        botManager.run();
    }
}
