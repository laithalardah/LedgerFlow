package com.example.accountservice.messaging;

import com.example.accountservice.messaging.command.ProcessTransferCommand;
import com.example.accountservice.service.AccountService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;


@Component
public class TransferConsumer {

    private final AccountService accountService;

    public TransferConsumer(AccountService accountService) {
        this.accountService = accountService;
    }

    @JmsListener(destination = "${process-transfer-queue}")
    public void consume(ProcessTransferCommand processTransferCommand) {

        accountService.ProcessTransfer(processTransferCommand);
    }
}
