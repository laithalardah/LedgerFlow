package com.example.paymentservice.service.impl;

import com.example.paymentservice.enums.Status;
import com.example.paymentservice.messaging.command.ProcessTransferCommand;
import com.example.paymentservice.entity.IdempotentKey;
import com.example.paymentservice.entity.TransferEntity;
import com.example.paymentservice.exception.InvalidTransferException;
import com.example.paymentservice.exception.DuplicateRequestException;
import com.example.paymentservice.mapper.TransferMapper;
import com.example.paymentservice.messaging.TransferCommandPublisher;
import com.example.paymentservice.model.TransferCreationModel;
import com.example.paymentservice.model.TransferModel;
import com.example.paymentservice.repository.IdempotentKeyRepository;
import com.example.paymentservice.repository.TransferRepository;
import com.example.paymentservice.service.AccountValidationService;
import com.example.paymentservice.service.TransferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;
    private final AccountValidationService accountValidationService;
    private final IdempotentKeyRepository idempotentKeyRepository;
    private final TransferMapper transferMapper;
    private final TransferCommandPublisher transferCommandPublisher;

    public TransferServiceImpl(TransferRepository transferRepository,
                               AccountValidationService accountValidationService,
                               TransferMapper transferMapper,
                               IdempotentKeyRepository idempotentKeyRepository ,
                               TransferCommandPublisher transferCommandPublisher) {

        this.transferRepository = transferRepository;
        this.accountValidationService = accountValidationService;
        this.transferMapper = transferMapper;
        this.idempotentKeyRepository = idempotentKeyRepository;
        this.transferCommandPublisher = transferCommandPublisher;

    }


    @Override
    @Transactional
    public TransferModel createTransfer(TransferCreationModel transferCreationModel , Long key) {

        if(idempotentKeyRepository.existsById(key)) {
            throw new DuplicateRequestException("Request already made");
        }

        log.info("Created New Request..");
        idempotentKeyRepository.save(new IdempotentKey(key));

        if(transferCreationModel.debtorAccountNumber().equals(transferCreationModel.creditorAccountNumber()))
            throw new InvalidTransferException("Can Not Transfer to the Same Account");

        accountValidationService.validateAccount(transferCreationModel.debtorAccountNumber());
        accountValidationService.validateAccount(transferCreationModel.creditorAccountNumber());

        accountValidationService.validateBalance(transferCreationModel.debtorAccountNumber() ,
                transferCreationModel.amount());

        TransferEntity transferEntity = transferMapper.toTransferEntity(transferCreationModel);

        transferRepository.save(transferEntity);

        ProcessTransferCommand processTransferCommand = new ProcessTransferCommand(
                transferEntity.getId(),
                transferEntity.getDebtorAccountNumber(),
                transferEntity.getCreditorAccountNumber(),
                transferEntity.getAmount()
        );

        transferCommandPublisher.Publish(processTransferCommand);

        return transferMapper.toTransferModel(transferEntity);
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
