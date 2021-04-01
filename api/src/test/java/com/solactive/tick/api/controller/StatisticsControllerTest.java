package com.solactive.tick.api.controller;

import com.solactive.tick.api.domain.IndexStatsMessage;
import com.solactive.tick.api.domain.InstrumentStat;
import com.solactive.tick.api.domain.MessageEvent;
import com.solactive.tick.api.domain.StatPrint;
import com.solactive.tick.api.service.StatisticsService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Map;

import static com.spotify.hamcrest.pojo.IsPojo.pojo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class StatisticsControllerTest extends AbstractControllerTest {

    private static final String DUMMY_INS = "INS";

    @Autowired
    StatisticsService statisticsService;


    @BeforeEach
    public void setStatsData() {

        var dummyStat = StatPrint.builder()
                .avg(BigDecimal.ONE)
                .count(1)
                .max(BigDecimal.ONE)
                .min(BigDecimal.ONE)
                .build();

        var indexStatsMessage = IndexStatsMessage.builder()
                .updatedAt(Instant.now().toEpochMilli())
                .all(dummyStat)
                .instruments(Map.of(DUMMY_INS, dummyStat))
                .build();

        var event = new MessageEvent<>(indexStatsMessage);
        statisticsService.onNewStatsAvailable(event);
    }

    @Test
    public void getInstrumentStatistic_Success() throws URISyntaxException {
        var response = this.restTemplate.getForEntity(getUri("/statistics/" + DUMMY_INS), InstrumentStat.class);

        Assert.assertEquals(200, response.getStatusCodeValue());

        assertThat(response.getBody(), is(pojo(InstrumentStat.class)
                .withProperty("count", is(1))
                .withProperty("avg", is(BigDecimal.ONE))
                .withProperty("max", is(BigDecimal.ONE))
                .withProperty("min", is(BigDecimal.ONE))));
    }

    @Test
    public void getInstrumentStatistic_Fail() throws URISyntaxException {
        var response = this.restTemplate.getForEntity(getUri("/statistics/" + "NOT_AVAILABLE"), InstrumentStat.class);
        Assert.assertEquals(204, response.getStatusCodeValue());
    }


    @Test
    public void getAllStatistic_Success() throws URISyntaxException {
        var response = this.restTemplate.getForEntity(getUri("/statistics/"), InstrumentStat.class);
        Assert.assertEquals(200, response.getStatusCodeValue());

        assertThat(response.getBody(), is(pojo(InstrumentStat.class)
                .withProperty("count", is(1))
                .withProperty("avg", is(BigDecimal.ONE))
                .withProperty("max", is(BigDecimal.ONE))
                .withProperty("min", is(BigDecimal.ONE))));
    }
}
