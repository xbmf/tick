package com.solactive.tick.api.controller;

import com.solactive.tick.api.domain.TickInput;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.Instant;

import static java.time.temporal.ChronoUnit.MINUTES;


public class IndexControllerTest extends AbstractControllerTest {

    @Test
    public void testCreateTick_Success() throws URISyntaxException {

        TickInput input = TickInput.builder()
                .instrument("ASD")
                .price(new BigDecimal(100))
                .timestamp(Instant.now().toEpochMilli())
                .build();

        var request = new HttpEntity<>(input);

        var response = this.restTemplate.postForEntity(getUri("/ticks"), request, String.class);

        Assert.assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    public void testCreateTick_WithPastTimestamp_Fail() throws URISyntaxException {
        // tick input from future
        TickInput input = TickInput.builder()
                .instrument("ASD")
                .price(new BigDecimal("100.10"))
                .timestamp(Instant.now().minus(2, MINUTES).toEpochMilli())
                .build();

        var request = new HttpEntity<>(input);

        var response = this.restTemplate.postForEntity(getUri("/ticks"), request, String.class);

        Assert.assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    public void testCreateTick_WithFutureTimestamp_Fail() throws URISyntaxException {
        // tick input from future
        TickInput input = TickInput.builder()
                .instrument("ASD")
                .price(new BigDecimal("100.10"))
                .timestamp(Instant.now().plus(2, MINUTES).toEpochMilli())
                .build();

        var request = new HttpEntity<>(input);

        var response = this.restTemplate.postForEntity(getUri("/ticks"), request, String.class);

        Assert.assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    public void testCreateTick_WithNegativeValue_Fail() throws URISyntaxException {
        TickInput input = TickInput.builder()
                .instrument("ASD")
                .price(new BigDecimal("-100.10"))
                .timestamp(Instant.now().toEpochMilli())
                .build();

        var request = new HttpEntity<>(input);

        var response = this.restTemplate.postForEntity(getUri("/ticks"), request, String.class);

        Assert.assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    public void testCreateTick_WithValueZero_Fail() throws URISyntaxException {
        TickInput input = TickInput.builder()
                .instrument("ASD")
                .price(BigDecimal.ZERO)
                .timestamp(Instant.now().toEpochMilli())
                .build();

        var request = new HttpEntity<>(input);

        var response = this.restTemplate.postForEntity(getUri("/ticks"), request, String.class);

        Assert.assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    public void testCreateTick_WithOneDigit_Success() throws URISyntaxException {
        TickInput input = TickInput.builder()
                .instrument("ASD")
                .price(new BigDecimal("100.1"))
                .timestamp(Instant.now().toEpochMilli())
                .build();

        var request = new HttpEntity<>(input);

        var response = this.restTemplate.postForEntity(getUri("/ticks"), request, String.class);

        Assert.assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    public void testCreateTick_WithMoreThanTwoDigits_Fail() throws URISyntaxException {
        // tick input from future
        TickInput input = TickInput.builder()
                .instrument("ASD")
                .price(new BigDecimal("100.345"))
                .timestamp(Instant.now().toEpochMilli())
                .build();

        var request = new HttpEntity<>(input);

        var response = this.restTemplate.postForEntity(getUri("/ticks"), request, String.class);

        Assert.assertEquals(204, response.getStatusCodeValue());
    }
}
