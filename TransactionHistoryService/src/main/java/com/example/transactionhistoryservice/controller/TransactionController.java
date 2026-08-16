package com.example.transactionhistoryservice.controller;

import com.example.transactionhistoryservice.mapper.TransactionMapper;
import com.example.transactionhistoryservice.resource.TransactionResource;
import com.example.transactionhistoryservice.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("transactions/history")
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    public TransactionController(TransactionService transactionService, TransactionMapper transactionMapper) {
        this.transactionService = transactionService;
        this.transactionMapper = transactionMapper;
    }

    @Operation(description = "Returns All Transactions By A User From all Accounts")
    @GetMapping("/{userId}")
    ResponseEntity<List<TransactionResource>> getUserAccountsTransactions(@PathVariable Long userId) {

        List<TransactionResource> transactions = transactionService.getUserTransactionHistory(userId).stream().
                map(transactionMapper::toTransactionResource).toList();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}
