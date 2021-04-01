package com.solactive.tick.statistics.scheduler;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.quartz.SimpleScheduleBuilder.repeatSecondlyForever;

@Configuration
public class Config {
    private static final String AGGREGATE_STATS_JOB = "aggregateStatsJob";

    @Bean(AGGREGATE_STATS_JOB)
    public JobDetail createAggregateStatsJob() {
        return JobBuilder.newJob()
                .ofType(AggregateStatsJob.class)
                .storeDurably()
                .withIdentity(AGGREGATE_STATS_JOB, "statistics")
                .build();

    }

    @Bean
    public Trigger createAggregateStatsJobTrigger(@Qualifier(AGGREGATE_STATS_JOB) JobDetail job) {
        return TriggerBuilder.newTrigger()
                .forJob(job)
                .withIdentity(AGGREGATE_STATS_JOB, "statistics")
                .withSchedule(repeatSecondlyForever())
                .build();
    }


}
