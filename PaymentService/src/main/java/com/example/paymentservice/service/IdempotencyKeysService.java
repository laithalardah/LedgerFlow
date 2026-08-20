package com.example.paymentservice.service;


import com.example.paymentservice.model.TransferModel;

import java.util.Optional;
import java.util.UUID;

public interface IdempotencyKeysService {
    void idempotencyKeysCleanUp();

    void createIdempotencyRecord(TransferModel transferModel , UUID key);

    Optional<TransferModel> checkIdempotency(UUID key);
}
