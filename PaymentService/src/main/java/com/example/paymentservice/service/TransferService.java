package com.example.paymentservice.service;

import com.example.paymentservice.enums.Status;
import com.example.paymentservice.model.TransferCreationModel;
import com.example.paymentservice.model.TransferModel;

public interface TransferService {

    TransferModel createTransfer(TransferCreationModel transferCreationModel , Long key);

    Status getTransferStatus(Long id);

    TransferModel getTransferDetails(Long id);
}
