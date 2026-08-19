package com.example.paymentservice.service.impl;

import com.example.paymentservice.entity.IdempotencyRecord;
import com.example.paymentservice.model.TransferModel;
import com.example.paymentservice.repository.IdempotencyRecordRepository;
import com.example.paymentservice.service.IdempotencyKeysService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.hash;

@Service
public class IdempotencyKeysServiceImpl implements IdempotencyKeysService {

    private final IdempotencyRecordRepository idempotencyRecordRepository;

    public IdempotencyKeysServiceImpl(IdempotencyRecordRepository idempotencyRecordRepository) {
        this.idempotencyRecordRepository = idempotencyRecordRepository;
    }

    @Override
    @Transactional
    public void idempotencyKeysCleanUp() {
        idempotencyRecordRepository.deleteByExpiresAtBefore(LocalDateTime.now());
    }

    @Override
    @Transactional
    public void createIdempotencyRecord(TransferModel transferModel, UUID key) {

        IdempotencyRecord record = new IdempotencyRecord();
        record.setKey(key);
        record.setStatusCode(200);
        record.setResponseBody(transferModel);

        idempotencyRecordRepository.save(record);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TransferModel> checkIdempotency(UUID key) {

        return idempotencyRecordRepository.findById(key)
                .map(idempotencyRecord -> idempotencyRecord.getResponseBody());
    }
}
