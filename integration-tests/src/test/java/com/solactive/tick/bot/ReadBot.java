package com.solactive.tick.bot;

import com.solactive.tick.client.ApiClient;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Slf4j
public class ReadBot implements Runnable {

    private Set<String> instruments;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
            .withZone(ZoneId.systemDefault());


    public ReadBot(Set<String> instruments) {
        this.instruments = instruments;
    }

    @Override
    public void run() {
        var client = new ApiClient();
        instruments.forEach(ins -> send(client, ins));
        log.info("----------------------------------------------------");
        send(client, "ALL");
        log.info("\n");

    }

    private void send(ApiClient client, String ins) {
        try {
            var time = formatter.format(Instant.now());
            var result = client.getStatsForInstrument(ins);
            if (result.getStatusCodeValue() == 200) {
                var stat = result.getBody();
                log.info("{} | {}   avg:{}  min:{}  max:{}  count:{}    ",
                        time, ins, stat.getAvg(), stat.getMin(), stat.getMax(), stat.getCount());
            } else {
                log.error("error for the instrument: {}, code:{}", ins, result.getStatusCodeValue());
            }
        } catch (Exception ex) {
            log.error("error while reading instrument:" + ins, ex);
        }
    }
}
