package com.solactive.tick.statistics.scheduler;

import com.solactive.tick.statistics.broker.producer.Producer;
import com.solactive.tick.statistics.service.StatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;

import static java.time.temporal.ChronoUnit.SECONDS;

@Slf4j
@DisallowConcurrentExecution
public class AggregateStatsJob implements Job {

    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private Producer producer;

    @Value("${statistics.constraints.sliding-time-interval}")
    private int slidingTimeInterval;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        long after = Instant.now().minus(slidingTimeInterval, SECONDS).toEpochMilli();
        var message = statisticsService.generateIndexStats(after);
        if (message.getAll().getCount() > 0) {
            producer.publishStats(statisticsService.generateIndexStats(after));
        } else {
            log.warn("no stats available");
        }
    }
}
