package com.example.paymentservice.config;

import com.example.paymentservice.job.IdempotencyKeysCleanUpJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail IdempotencyKeysCleanUpJobDetail() {
        return JobBuilder
                .newJob(IdempotencyKeysCleanUpJob.class)
                .withIdentity("IdempotencyKeysCleanUpJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger IdempotencyKeysCleanUpTrigger(
            JobDetail IdempotencyKeysCleanUpJobDetail){

        return TriggerBuilder
                .newTrigger()
                .forJob(IdempotencyKeysCleanUpJobDetail)
                .withIdentity("IdempotencyKeysCleanUpTrigger")
                .startNow()
                .withSchedule(
                        CronScheduleBuilder
                                .cronSchedule("0 0 2 * * ?")
                )
                .build();
    }
}
