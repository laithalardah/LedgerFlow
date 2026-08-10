package com.example.paymentservice.service.impl;

import com.example.paymentservice.entity.TransferEntity;
import com.example.paymentservice.enums.Status;
import com.example.paymentservice.exception.InvalidTransferException;
import com.example.paymentservice.messaging.event.TransferCompleted;
import com.example.paymentservice.messaging.event.TransferFailed;
import com.example.paymentservice.repository.TransferRepository;
import com.example.paymentservice.service.TransferEventService;
import org.springframework.stereotype.Service;


@Service
public class TransferEventServiceImpl implements TransferEventService {

    private final TransferRepository transferRepository;

    public TransferEventServiceImpl(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    public void handleTransferCompleted(TransferCompleted transferCompleted) {
        Long id = transferCompleted.transferId();

        TransferEntity transferEntity = transferRepository.findById(id)
                .orElseThrow(() ->
                        new InvalidTransferException("Transfer Does Not Exist Anymore For Some Reason lol"));


        transferEntity.setStatus(Status.COMPLETE);
        transferRepository.save(transferEntity);

        //send notification that its completed
    }

    public void handleTransferFailed(TransferFailed transferFailed) {
        Long id = transferFailed.transferId();

        TransferEntity transferEntity = transferRepository.findById(id)
                .orElseThrow(() ->
                        new InvalidTransferException("Transfer Does Not Exist Anymore For Some Reason lol"));


        transferEntity.setStatus(Status.FAILED);
        transferRepository.save(transferEntity);

        //send notification that it failed
    }

}
