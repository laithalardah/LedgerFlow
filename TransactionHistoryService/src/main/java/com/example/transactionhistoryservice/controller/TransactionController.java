package com.example.transactionhistoryservice.controller;

import com.example.transactionhistoryservice.mapper.TransactionMapper;
import com.example.transactionhistoryservice.resource.TransactionResource;
import com.example.transactionhistoryservice.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
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
    ResponseEntity<Page<TransactionResource>> getUserAccountsTransactions(@PathVariable Long userId ,
                                                                          @RequestParam(defaultValue = "0") int page ,
                                                                          @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page , size);
        Page<TransactionResource> transactions = transactionService.getUserTransactionHistory(userId , pageable)
                .map(transactionMapper::toTransactionResource);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}
