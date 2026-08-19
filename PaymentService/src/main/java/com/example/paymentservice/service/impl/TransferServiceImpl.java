package com.example.paymentservice.service.impl;

import com.example.paymentservice.enums.Status;
import com.example.paymentservice.messaging.command.ProcessTransferCommand;
import com.example.paymentservice.entity.TransferEntity;
import com.example.paymentservice.exception.InvalidTransferException;
import com.example.paymentservice.mapper.TransferMapper;
import com.example.paymentservice.messaging.TransferPublisher;
import com.example.paymentservice.messaging.event.TransactionCreated;
import com.example.paymentservice.model.TransferCreationModel;
import com.example.paymentservice.model.TransferModel;
import com.example.paymentservice.repository.TransferRepository;
import com.example.paymentservice.service.AccountValidationService;
import com.example.paymentservice.service.IdempotencyKeysService;
import com.example.paymentservice.service.TransactionEventService;
import com.example.paymentservice.service.TransferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;
    private final AccountValidationService accountValidationService;
    private final TransferMapper transferMapper;
    private final TransferPublisher transferPublisher;
    private final TransactionEventService transactionEventService;
    private final IdempotencyKeysService idempotencyKeysService;

    public TransferServiceImpl(TransferRepository transferRepository,
                               AccountValidationService accountValidationService,
                               TransferMapper transferMapper,
                               TransferPublisher transferPublisher, TransactionEventService transactionEventService,
                               IdempotencyKeysService idempotencyKeysService) {

        this.transferRepository = transferRepository;
        this.accountValidationService = accountValidationService;
        this.transferMapper = transferMapper;
        this.transferPublisher = transferPublisher;
        this.transactionEventService = transactionEventService;
        this.idempotencyKeysService = idempotencyKeysService;
    }


    @Override
    @Transactional
    public TransferModel createTransfer(TransferCreationModel transferCreationModel , UUID requestKey) {

        Optional<TransferModel> idempotentTransferModel =
                idempotencyKeysService.checkIdempotency(requestKey);

        if(idempotentTransferModel.isPresent())
            return idempotentTransferModel.get();


        if(transferCreationModel.debtorAccountNumber().equals(transferCreationModel.creditorAccountNumber()))
            throw new InvalidTransferException("Can Not Transfer to the Same Account");

        accountValidationService.validateAccount(transferCreationModel.debtorAccountNumber());
        accountValidationService.validateAccount(transferCreationModel.creditorAccountNumber());

        accountValidationService.validateBalance(transferCreationModel.debtorAccountNumber() ,
                transferCreationModel.amount());

        TransferEntity transferEntity = transferMapper.toTransferEntity(transferCreationModel);

        transferRepository.save(transferEntity);

        TransferModel transferModel = transferMapper.toTransferModel(transferEntity);

        idempotencyKeysService.createIdempotencyRecord(transferModel , requestKey);

        ProcessTransferCommand processTransferCommand = new ProcessTransferCommand(
                transferEntity.getId(),
                transferEntity.getDebtorAccountNumber(),
                transferEntity.getCreditorAccountNumber(),
                transferEntity.getAmount()
        );



        transferPublisher.publish(processTransferCommand);

        TransactionCreated transactionCreated = new TransactionCreated(
                transferEntity.getId(),
                "Transfer",
                transferEntity.getDebtorAccountNumber(),
                transferEntity.getCreditorAccountNumber(),
                transferEntity.getAmount(),
                LocalDateTime.now()
        );

        transactionEventService.handleTransactionCreated(transactionCreated);

        return transferModel;
    }

    @Override
    @Transactional(readOnly = true)
    public Status getTransferStatus(Long id) {

        log.info("Getting Transfer Status");

        TransferEntity transferEntity = transferRepository.findById(id)
                .orElseThrow(() ->
                        new InvalidTransferException("Transfer Does Not Exist For some Reason"));

        return transferEntity.getStatus();
    }

    @Override
    @Transactional(readOnly = true)
    public TransferModel getTransferDetails(Long id) {

        log.info("Getting Transfer Details");

        TransferEntity transferEntity = transferRepository.findById(id)
                .orElseThrow(() ->
                        new InvalidTransferException("Transfer Does Not Exist For some Reason"));

        return transferMapper.toTransferModel(transferEntity);
    }

    @Override
    public Page<TransferModel> getPreviousTransfers(Long accountNumber , Pageable pageable) {
        log.info("Getting Previous Transfers");

        return transferRepository.findAllByDebtorAccountNumberOrCreditorAccountNumber(accountNumber , accountNumber ,
                        pageable)
                .map(transferMapper :: toTransferModel);
    }
}
