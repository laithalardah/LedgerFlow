package com.example.paymentservice.job;

import com.example.paymentservice.service.IdempotencyKeysService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

public class IdempotencyKeysCleanUpJob implements Job {

    private final IdempotencyKeysService idempotencyKeysService;

    public IdempotencyKeysCleanUpJob(IdempotencyKeysService idempotencyKeysService) {
        this.idempotencyKeysService = idempotencyKeysService;
    }


    @Override
    public void execute(JobExecutionContext context){
        idempotencyKeysService.idempotencyKeysCleanUp();
    }
}
