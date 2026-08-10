package com.example.accountservice.messaging;

import com.example.accountservice.messaging.command.ProcessTransferCommand;
import com.example.accountservice.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class TransferConsumer {

    private final AccountService accountService;

    public TransferConsumer(AccountService accountService) {
        this.accountService = accountService;
    }

    @JmsListener(destination = "${process-transfer-queue}")
    public void consume(ProcessTransferCommand processTransferCommand) {

        log.info("Processing Transfer of id "  + processTransferCommand.transferId());
        accountService.ProcessTransfer(processTransferCommand);
    }
}
