package com.example.paymentservice.service.impl;

import com.example.paymentservice.entity.TransferEntity;
import com.example.paymentservice.enums.Status;
import com.example.paymentservice.exception.InvalidTransferException;
import com.example.paymentservice.messaging.event.TransactionUpdated;
import com.example.paymentservice.messaging.event.TransferCompleted;
import com.example.paymentservice.messaging.event.TransferFailed;
import com.example.paymentservice.repository.TransferRepository;
import com.example.paymentservice.service.TransactionEventService;
import com.example.paymentservice.service.TransferEventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class TransferEventServiceImpl implements TransferEventService {

    private final TransferRepository transferRepository;
    private final TransactionEventService transactionEventService;

    public TransferEventServiceImpl(TransferRepository transferRepository, TransactionEventService transactionEventService) {
        this.transferRepository = transferRepository;
        this.transactionEventService = transactionEventService;
    }

    public void handleTransferCompleted(TransferCompleted transferCompleted) {
        Long id = transferCompleted.transferId();

        TransferEntity transferEntity = transferRepository.findById(id)
                .orElseThrow(() ->
                        new InvalidTransferException("Transfer Does Not Exist Anymore For Some Reason lol"));


        transferEntity.setStatus(Status.COMPLETE);
        transferRepository.save(transferEntity);

        log.info("Transfer Status Successfully Updated to Completed");

        TransactionUpdated transactionUpdated = new TransactionUpdated(
                "Transfer",
                transferEntity.getId(),
                Status.COMPLETE
        );

        transactionEventService.handleTransactionUpdated(transactionUpdated);
        //send notification that its completed
    }

    public void handleTransferFailed(TransferFailed transferFailed) {
        Long id = transferFailed.transferId();

        TransferEntity transferEntity = transferRepository.findById(id)
                .orElseThrow(() ->
                        new InvalidTransferException("Transfer Does Not Exist Anymore For Some Reason lol"));


        transferEntity.setStatus(Status.FAILED);
        transferRepository.save(transferEntity);

        log.info("Transfer Status Successfully Updated to Failed");
        TransactionUpdated transactionUpdated = new TransactionUpdated(
                "Transfer",
                transferEntity.getId(),
                Status.FAILED
        );

        transactionEventService.handleTransactionUpdated(transactionUpdated);
        //send notification that it failed
    }
}
