package com.solactive.tick.client;

import com.solactive.tick.domain.InstrumentStat;
import com.solactive.tick.domain.TickInput;
import lombok.val;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

public class ApiClient {
    private static final String API_GATEWAY = "http://localhost:9001/";

    URI getUri(String path) throws URISyntaxException {
        val url = API_GATEWAY + path;
        return new URI(url);
    }

    public int tick(TickInput tickInput) throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        var request = new HttpEntity<>(tickInput);
        var response = restTemplate.postForEntity(getUri("ticks"), request, String.class);
        return response.getStatusCodeValue();
    }

    public ResponseEntity<InstrumentStat> getStatsForAll() throws URISyntaxException {
        var restTemplate = new RestTemplate();
        return restTemplate.getForEntity(getUri("statistics"), InstrumentStat.class);
    }

    public ResponseEntity<InstrumentStat> getStatsForInstrument(String instrument) throws URISyntaxException {
        if (instrument.equals("ALL")) {
            return this.getStatsForAll();
        }
        var restTemplate = new RestTemplate();
        return restTemplate.getForEntity(getUri("statistics/" + instrument), InstrumentStat.class);
    }
}
