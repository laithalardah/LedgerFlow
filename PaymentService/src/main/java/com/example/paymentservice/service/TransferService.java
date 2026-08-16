package com.example.paymentservice.service;

import com.example.paymentservice.enums.Status;
import com.example.paymentservice.model.TransferCreationModel;
import com.example.paymentservice.model.TransferModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransferService {

    TransferModel createTransfer(TransferCreationModel transferCreationModel , Long key);

    Status getTransferStatus(Long id);

    TransferModel getTransferDetails(Long id);

    Page<TransferModel> getPreviousTransfers(Long accountNumber , Pageable pageable);
}
