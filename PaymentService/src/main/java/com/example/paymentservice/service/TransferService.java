package com.example.paymentservice.service;

import com.example.paymentservice.enums.Status;
import com.example.paymentservice.model.TransferCreationModel;
import com.example.paymentservice.model.TransferModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TransferService {

    TransferModel createTransfer(TransferCreationModel transferCreationModel , UUID requestKey);

    Status getTransferStatus(Long id);

    TransferModel getTransferDetails(Long id);

    Page<TransferModel> getPreviousTransfers(Long accountNumber , Pageable pageable);
}
