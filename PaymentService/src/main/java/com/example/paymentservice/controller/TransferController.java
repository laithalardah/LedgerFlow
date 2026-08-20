package com.example.paymentservice.controller;

import com.example.paymentservice.enums.Status;
import com.example.paymentservice.mapper.TransferMapper;
import com.example.paymentservice.model.TransferCreationModel;
import com.example.paymentservice.model.TransferModel;
import com.example.paymentservice.service.TransferService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.paymentservice.resource.TransferCreationResource;
import com.example.paymentservice.resource.TransferResource;
import org.springframework.data.domain.Pageable;

import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/transfers")
public class TransferController {

    private final TransferMapper transferMapper;
    private final TransferService transferService;

    public TransferController(TransferMapper transferMapper , TransferService transferService) {
        this.transferMapper = transferMapper;
        this.transferService = transferService;
    }

    @Operation(description = "Creates a New Transfer , requires Idempotency key with Headers")
    @PostMapping("/")
    public ResponseEntity<TransferResource> createTransfer(@RequestBody @Valid TransferCreationResource transferCreationResource ,
                                                           @RequestHeader("x-Idempotency-key") UUID key) {

        log.info("Create Transfer Endpoint Invoked");
        TransferCreationModel transferCreationModel = transferMapper.toTransferCreationModel(transferCreationResource);

        TransferModel createdTransferModel = transferService.createTransfer(transferCreationModel , key);

        return ResponseEntity.ok(transferMapper.toTransferResource(createdTransferModel));
    }

    @Operation(description = "Get Transfer Status")
    @GetMapping("/{transferId}/status")
    public ResponseEntity<Status> getTransferStatus(@PathVariable Long transferId) {

        log.info("Transfer Status Endpoint Invoked");
        return ResponseEntity.ok(transferService.getTransferStatus(transferId));
    }

    @Operation(description = "Get Transfer Details")
    @GetMapping("/{transferId}/details")
    public ResponseEntity<TransferResource> getTransferDetails(@PathVariable Long transferId) {

        log.info("Transfer Details Endpoint invoked");

        TransferModel transferModel = transferService.getTransferDetails(transferId);
        return ResponseEntity.ok(transferMapper.toTransferResource(transferModel));
    }


    @Operation(description = "Gets Previous Transfers")
    @GetMapping("{accountNumber}")
    public ResponseEntity<Page<TransferResource>> getPreviousTransfers(@PathVariable Long accountNumber,
        @RequestParam(defaultValue = "0") int page ,
        @RequestParam(defaultValue = "5") int size
    ){
        Pageable pageable = PageRequest.of(page , size , Sort.by("id").descending());

        Page<TransferResource> transferResources = transferService.getPreviousTransfers(accountNumber, pageable)
                .map(transferMapper :: toTransferResource);

        return ResponseEntity.ok(transferResources);
    }

}
